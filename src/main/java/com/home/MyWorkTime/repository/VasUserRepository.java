package com.home.MyWorkTime.repository;

import com.home.MyWorkTime.entity.VasUserModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VasUserRepository extends JpaRepository<VasUserModel, Long> {

    @Override
    @NotNull
    Optional<VasUserModel> findById (@NotNull Long id);

    VasUserModel findByUserName(String user_name);
}
