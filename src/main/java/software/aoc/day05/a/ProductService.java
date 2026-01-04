package software.aoc.day05.a;

import java.util.List;

public class ProductService {

    public long countValidProducts(List<Long> ids, ValidationPolicy policy) {
        return ids.stream()
                .filter(policy::isValid)
                .count();
    }
}