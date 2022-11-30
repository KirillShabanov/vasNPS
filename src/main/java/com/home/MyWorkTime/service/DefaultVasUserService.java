package com.home.MyWorkTime.service;


import com.home.MyWorkTime.entity.VasUserModel;
import com.home.MyWorkTime.entity.VasUserModelDTO;
import com.home.MyWorkTime.exception.ValidationException;
import com.home.MyWorkTime.repository.VasUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class DefaultVasUserService implements VasUserService {

    private final VasUserRepository vasUserRepository;
    private final VasUserConverter vasUserConverter;


    public DefaultVasUserService(VasUserRepository vasUserRepository, VasUserConverter vasUserConverter) {
        this.vasUserRepository = vasUserRepository;
        this.vasUserConverter = vasUserConverter;
    }


    @Override
    public VasUserModelDTO saveUser(VasUserModelDTO vasUserModelDTO) throws ValidationException {
        validateUsersModelDTO(vasUserModelDTO);
        VasUserModel savedUser = vasUserRepository.save(vasUserConverter.fromVasUserModelDTOToVasUsersModel(vasUserModelDTO));
        return vasUserConverter.fromVasUserModelToVasUserModelDTO(savedUser);
    }

    private void validateUsersModelDTO(VasUserModelDTO vasUserModelDTO) throws ValidationException {
        if (isNull(vasUserModelDTO)){
            throw new ValidationException("Object user is null");
        }
    }

    @Override
    public void deleteUser(Long userId) {
        vasUserRepository.deleteById(userId);
    }

    @Override
    public List<VasUserModelDTO> findAll() {
        return vasUserRepository.findAll()
                .stream()
                .map(vasUserConverter::fromVasUserModelToVasUserModelDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VasUserModel> findById(Long id) {
        return vasUserRepository.findById(id);
    }

}
