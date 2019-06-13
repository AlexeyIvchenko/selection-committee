package ru.military.committee.repository;

import ru.military.committee.domain.personal.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Byte> {
}
