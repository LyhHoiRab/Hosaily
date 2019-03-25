package com.lab.hosaily.core.app.service;



import com.lab.hosaily.core.app.entity.Customer;
import org.springframework.web.multipart.MultipartFile;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;

import java.util.List;

public interface CustomerService {

    void save(Customer customer);

    void update(Customer customer);

    Customer getById(String id);

    void delete(String id);

    Page<Customer> page(PageRequest pageRequest, String name,
                        String situation, String startTime, String endTime, String process, String follower);

    Page<Customer> pageForSeller(PageRequest pageRequest, String name, String channel, String customerService);

//    List<Customer> findAllByNameChannelCS(String name, String channel, String customerService);

    List<String> importFile(MultipartFile file, String uploader) throws Exception;

    List<Customer> findAllByMix(String name, String situation, String startTime, String endTime);
}
