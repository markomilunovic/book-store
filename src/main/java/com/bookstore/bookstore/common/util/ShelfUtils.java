package com.bookstore.bookstore.common.util;

import com.bookstore.bookstore.entity.Shelf;
import com.bookstore.bookstore.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ShelfUtils {

    private final ShelfRepository shelfRepository;

    @Autowired
    public ShelfUtils(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    /**
     * Retrieves or creates shelves based on a set of shelf codes.
     * <p>
     * For each shelf code in the provided set, this method checks the repository to see if a
     * shelf with the specified code already exists. If a shelf is found, it is returned; otherwise,
     * a new shelf is created, saved to the repository, and returned. The method ensures that
     * only unique shelves (by code) are included in the resulting set.
     * </p>
     *
     * @param shelfCodes a set of shelf codes representing shelves to be retrieved or created
     * @return a set of {@link Shelf} entities that are either retrieved from the repository or newly created
     */
    public Set<Shelf> getOrCreateShelves(Set<String> shelfCodes) {
        return shelfCodes.stream()
                .map(code -> shelfRepository.findByCode(code).orElseGet(() -> {
                    Shelf newShelf = new Shelf();
                    newShelf.setCode(code);
                    newShelf.setSection(String.valueOf(code.charAt(0)));
                    return shelfRepository.save(newShelf);
                }))
                .collect(Collectors.toSet());
    }
}
