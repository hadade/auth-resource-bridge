package org.resource.api.rest.external.dto;

import lombok.Data;

import java.util.List;

@Data
public class GraphQLResponse<T> {
    private T data;
    private List<GraphQLError> errors;

    @Data
    public static class GraphQLError {
        private String message;
        private List<Location> locations;
        private List<String> path;

        @Data
        public static class Location {
            private int line;
        }
    }
}
