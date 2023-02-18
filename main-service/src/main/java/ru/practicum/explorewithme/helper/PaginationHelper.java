package ru.practicum.explorewithme.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PaginationHelper {
    default Pageable getPagination(int from, int size, String orderby, Sort.Direction order) {
        Sort sortBy = Sort.by(order, orderby);
        int page = from / size;
        return PageRequest.of(page, size, sortBy);
    }
}
