package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.mapper.CategoryMapper;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.CategoryRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public CategoryDto update(String name, Long categoryId, Long userId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId,userId);
        if(category != null){
            category.setName(name);
            return CategoryMapper.INSTANCE.toDto(categoryRepository.save(category));
        }else {
            throw  new RuntimeException("Category not found");
        }
    }

    @Override
    public CategoryDto findByCategoryIdAndUserId(Long categoryId, Long userId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId,userId);
        if(category != null){
            return CategoryMapper.INSTANCE.toDto(category);
        }else {
            throw  new RuntimeException("Category not found");
        }
    }

    @Override
    public void delete(Long categoryId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Category category = categoryRepository.findByIdAndUserId(categoryId,userId);
            if(category != null){
                user.getCategories().remove(category);
                category.setUser(null);
                categoryRepository.delete(category);
            }else {
                throw  new RuntimeException("Category not found");
            }
        }else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Set<CategoryDto> getListByIdAndUserId(List<String> categoryIdList, Long userId) {
        Set<CategoryDto> categoryDtoList = new HashSet<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            for(String categoryId : categoryIdList){
                Category category = categoryRepository.findByIdAndUserId(Long.valueOf(categoryId),userId);
                if(category != null){
                    categoryDtoList.add(CategoryMapper.INSTANCE.toDto(category));
                }else {
                    throw  new RuntimeException("Category not found");
                }
            }
        }else {
            throw new RuntimeException("User not found");
        }
        return categoryDtoList;
    }
}
