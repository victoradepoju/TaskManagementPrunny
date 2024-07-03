package com.victor.task_management_with_security.utils;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResponse <T> (
        List<T> content, // by converting Pageable to a list
        int number,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
){
}
