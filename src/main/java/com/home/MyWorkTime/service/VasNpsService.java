package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasNpsModel;
import com.home.MyWorkTime.repository.VasNpsRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Slf4j
@Service
@NoArgsConstructor
public class VasNpsService {

    private VasNpsRepository vasNpsRepository;

    @Autowired
    public VasNpsService(VasNpsRepository vasNpsRepository) {
        this.vasNpsRepository = vasNpsRepository;
    }

    public VasNpsModel saveOrder(VasNpsModel vasNpsModel) {

        Date dateOrder = vasNpsModel.getDate_order();
        Date dateCall = new Date();
        dateCall.setTime(dateOrder.getTime() + 2 * 24 * 60 * 60 * 1000);
        vasNpsModel.setCall_date(dateCall);
        VasNpsModel savedOrder = vasNpsRepository.save(vasNpsModel);

        return VasNpsRepository.saveOrder(savedOrder);
    }

    public void updateCallDate(int num_order, String call_date) {
        vasNpsRepository.updateCallDate(num_order, call_date);
    }

    public void npsCall(int num_order, int nps, String admin_comment, String admin_name, String call_status) {
        vasNpsRepository.npsCall(num_order, nps, admin_comment, admin_name, call_status);
    }
}
