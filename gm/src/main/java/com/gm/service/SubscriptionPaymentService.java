package com.gm.service;

import com.gm.model.Subscription;
import com.gm.model.SubscriptionPayment;
import com.gm.repository.SubscriptionPaymentRepository;
import com.gm.repository.SubscriptionRepository;
import com.gm.util.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionPaymentService {
    @Autowired
    private SubscriptionPaymentRepository subsPayRepo;

    public List<SubscriptionPayment> getAll(){
        List<SubscriptionPayment> list = new ArrayList<SubscriptionPayment>();
        Iterable<SubscriptionPayment> subsIterator = subsPayRepo.findAll();
        for (SubscriptionPayment subs : subsIterator){
            list.add(subs);
        }
        return list;
    }

    public SubscriptionPayment create(SubscriptionPayment subs){
        return subsPayRepo.save(subs);
    }

    public boolean delete(Long id){
        Optional<SubscriptionPayment> subsData = subsPayRepo.findById(id);
        if (subsData.isPresent()) {
            subsPayRepo.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public SubscriptionPayment getById(Long id){
        Optional<SubscriptionPayment> subsData = subsPayRepo.findById(id);
        if(subsData.isPresent()){
            SubscriptionPayment product = subsData.get();
            return product;
        }else{
            return null; //posteriormente tratar isso com exceção
        }
    }


}
