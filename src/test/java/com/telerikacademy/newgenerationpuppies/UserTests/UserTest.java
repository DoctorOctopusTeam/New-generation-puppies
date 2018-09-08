package com.telerikacademy.newgenerationpuppies.UserTests;

import com.sun.deploy.net.HttpRequest;
import com.telerikacademy.newgenerationpuppies.DTO.TopTenDTO;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import com.telerikacademy.newgenerationpuppies.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    @Mock
    UserRepository mockRepository;

    @InjectMocks
    UserServiceImpl service;

    @Test
    public void testGetTopTenSubscribers(){
        TopTenDTO test =  new TopTenDTO();
        TopTenDTO test1 = new TopTenDTO();
        List<TopTenDTO> list = new ArrayList<>();
        list.add(test);
        list.add(test1);
        Mockito.when(mockRepository.getBiggestAmountsPaidBySubscribers(null)).thenReturn(list);
        List<TopTenDTO> result = service.getBiggestAmountsPaidBySubscribers(null);
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testGetAllPayments(){
        Bill test =  new Bill();
        Bill test1 =  new Bill();
        Bill test2 =  new Bill();
        Bill test3 =  new Bill();
        Bill test4 =  new Bill();
        List<Bill> list = new ArrayList<>();
        list.add(test);
        list.add(test1);
        list.add(test2);
        list.add(test3);
        list.add(test4);
        Mockito.when(mockRepository.getAllPayments(null)).thenReturn(list);
        List<Bill> result = service.getAllPayments(null);
        Assert.assertEquals(5, result.size());
    }

    @Test
    public void testGetAllPayments_with_No_values(){
        Mockito.when(mockRepository.getAllPayments(null)).thenReturn(new ArrayList<Bill>());
        List<Bill> result = service.getAllPayments(null);
        Assert.assertEquals(0, result.size());
    }

}
