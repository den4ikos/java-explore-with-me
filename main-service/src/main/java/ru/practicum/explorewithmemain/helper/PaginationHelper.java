package ru.practicum.explorewithmemain.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public interface PaginationHelper {

    default PageRequest getPagination(int from, int size, String orderby, Sort.Direction order) {
        Sort sortBy = Sort.by(order, orderby);
        int page = from / size;
        return PageRequest.of(page, size, sortBy);
    }
}
