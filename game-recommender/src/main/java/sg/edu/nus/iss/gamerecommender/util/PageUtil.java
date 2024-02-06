package sg.edu.nus.iss.gamerecommender.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public final class PageUtil {
	public static <T> Page<T> getPage(int page, int pageSize, List<T> list) {
        Pageable pageRequest = PageRequest.of(page, pageSize);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());

        List<T> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, list.size());
    }
}