/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  
 * 
 *  Date of remmark: 27/07/2023
 */
package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.repository.VasManagerNpsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VasManagerNpsService {

    private final VasManagerNpsRepository vasManagerNpsRepository;

    public VasManagerNpsService(VasManagerNpsRepository vasManagerNpsRepository) {
        this.vasManagerNpsRepository = vasManagerNpsRepository;
    }

    public VasManagerNpsModel saveNpsCall(VasManagerNpsModel vasManagerNpsModel) {
        VasManagerNpsModel saveNpsCall = vasManagerNpsRepository.save(vasManagerNpsModel);
        return VasManagerNpsRepository.saveNpsCall(saveNpsCall);
    }

    public List<VasManagerNpsModel> findAllNpsTo() {
        return vasManagerNpsRepository.forMailTo();
    }

    public List<VasManagerNpsModel> findAllNpsCc() {
        return vasManagerNpsRepository.forMailCopy();
    }

    public ResponseEntity<Void> deleteNpsTo(Long id) {
        vasManagerNpsRepository.deleteNpsTo(id);
        return null;
    }

    public List<VasManagerNpsModel> findAllTechnical() {
        return vasManagerNpsRepository.findAllTechnical();
    }

    public List<VasManagerNpsModel> findAllBodyRepair() {
        return vasManagerNpsRepository.findAllBodyRepair();
    }
}
