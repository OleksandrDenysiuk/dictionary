package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.mapper.CategoryMapper;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.CategoryRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto create(String name, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Category category = Category.builder().name(name).user(user).build();
            user.getCategories().add(category);
            return CategoryMapper.INSTANCE.toDto(categoryRepository.save(category));
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public List<CategoryDto> findAll(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getCategories().stream()
                    .sorted(Comparator.comparing(Category::getId))
                    .map(CategoryMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public CategoryDto update(String name, Long categoryId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<Category> optionalCategory = optionalUser.get().getCategories().stream()
                    .filter(category -> category.getId().equals(categoryId))
                    .findFirst();
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                category.setName(name);
                return CategoryMapper.INSTANCE.toDto(categoryRepository.save(category));
            } else {
                throw new RuntimeException("Category not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public CategoryDto findByCategoryIdAndUserId(Long categoryId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<Category> optionalCategory = optionalUser.get().getCategories().stream()
                    .filter(category -> category.getId().equals(categoryId))
                    .findFirst();
            if (optionalCategory.isPresent()) {
                return CategoryMapper.INSTANCE.toDto(optionalCategory.get());
            } else {
                throw new RuntimeException("Category not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void delete(Long categoryId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Category> optionalCategory = user.getCategories().stream()
                    .filter(category -> category.getId().equals(categoryId))
                    .findFirst();
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                user.getCategories().remove(category);
                category.setUser(null);
                categoryRepository.delete(category);
            } else {
                throw new RuntimeException("Category not found");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Set<CategoryDto> getListByIdAndUserId(List<String> categoryIdList, Long userId) {
        Set<CategoryDto> categoryDtoList = new HashSet<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            for (String categoryId : categoryIdList) {
                Optional<Category> optionalCategory = user.getCategories().stream()
                        .filter(category -> category.getId().equals(categoryId))
                        .findFirst();
                if (optionalCategory.isPresent()) {
                    categoryDtoList.add(CategoryMapper.INSTANCE.toDto(optionalCategory.get()));
                } else {
                    throw new RuntimeException("Category not found");
                }
            }
        } else {
            throw new RuntimeException("User not found");
        }
        return categoryDtoList;
    }
}
