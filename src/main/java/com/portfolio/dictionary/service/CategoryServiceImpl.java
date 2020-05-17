package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.mapper.CategoryMapper;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final UserRepository userRepository;

    public CategoryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Category create(String name, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Category category = Category.builder().name(name).user(user).build();
            user.getCategories().add(category);
            User userSave = userRepository.save(user);

            Optional<Category> optionalCategory = userSave.getCategories()
                    .stream()
                    .filter(category1 -> (category1.getName().equals(name))).findAny();

            if (optionalCategory.isPresent()) {
                return optionalCategory.get();
            } else {
                throw new RuntimeException("Category not saved");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Category update(String name, Long categoryId, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Category> categoryOptional = user.getCategories().stream()
                    .filter(category -> categoryId.equals(category.getId())).findAny();

            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                category.setName(name);
                user.getCategories().add(category);
                User userSave = userRepository.save(user);
                Optional<Category> categoryOptionalSave = userSave.getCategories().stream()
                        .filter(category1 -> categoryId.equals(category.getId())).findAny();
                if (categoryOptionalSave.isPresent()) {
                    return categoryOptionalSave.get();
                } else {
                    throw new RuntimeException("Category not found");
                }
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
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            Optional<Category> categoryFound = user.getCategories().stream().filter(category -> categoryId.equals(category.getId())).findFirst();
            return categoryFound.map(CategoryMapper.INSTANCE::toDto).orElse(null);
        }else {
            throw  new RuntimeException("User not found");
        }
    }

    @Override
    public void delete(Long categoryId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Category> categoryFound = user.getCategories().stream().filter(category -> categoryId.equals(category.getId())).findFirst();
            if (categoryFound.isPresent()) {
                Category category = categoryFound.get();
                user.getCategories().remove(category);
                category.setUser(null);
                userRepository.save(user);
            }
        }
    }
}
