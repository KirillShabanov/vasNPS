package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasUserModel;
import com.home.MyWorkTime.entity.VasUserModelDTO;
import com.home.MyWorkTime.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public interface VasUserService {

    VasUserModelDTO saveUser(VasUserModelDTO vasUserModelDTO) throws ValidationException;

    Optional<VasUserModel> findById(Long id);

    List<VasUserModelDTO> findAll();

    void deleteUser(Long id);
}
