package org.resource.api.rest.external;

import lombok.extern.log4j.Log4j2;
import org.resource.api.rest.external.dto.GraphQLResponse;
import org.resource.api.rest.external.dto.ValidateTokenData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Log4j2
@Service
public class AuthExternalService {

    private final RestTemplate restTemplate;
    private final String graphqlAuthUrl;

    public AuthExternalService(
            RestTemplate restTemplate,
            @Value("${auth.graphql.url}") String graphqlAuthUrl
    ) {
        this.restTemplate = restTemplate;
        this.graphqlAuthUrl = graphqlAuthUrl;
    }

    public Optional<ValidateTokenData.ValidateTokenResult> validateToken(String token) {
        String graphqlQuery = """
        {
          "query": "mutation validate($token: TokenValidationRequest!) { validateToken(request: $token) { valid username } }",
          "variables": {
            "token": {
              "token": "%s"
            }
          }
        }
        """.formatted(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(graphqlQuery, headers);

        try {
            ResponseEntity<GraphQLResponse<ValidateTokenData>> response = restTemplate.exchange(
                    graphqlAuthUrl,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {
                    }
            );

            GraphQLResponse<ValidateTokenData> graphQLResponse = response.getBody();

            if (graphQLResponse.getErrors() != null && !graphQLResponse.getErrors().isEmpty()) {
                log.error("GraphQL error for validateToken call: {}", graphQLResponse.getErrors());
                return Optional.empty();
            } else {
                return Optional.ofNullable(graphQLResponse.getData().getValidateToken());
            }
        } catch (Exception e) {
            log.error("GraphQL error during validateToken call: {}", e.getMessage());
            return Optional.empty();
        }
    }
}

