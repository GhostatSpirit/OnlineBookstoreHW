package com.lykavin.bookstore.controller;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.model.OrderEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.repository.BookRepository;
import com.lykavin.bookstore.repository.OrderRepository;
import com.lykavin.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by lykav on 2017/4/23.
 */
@Controller
@RequestMapping("/admin/orders*")
public class OrderController {

    @Autowired
    OrderRepository orderRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    BookRepository bookRepo;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getOrders(ModelMap modelMap){
        // 查询order中所有记录
        List<OrderEntity> orderList = orderRepo.findAll();

        // 将所有记录传递给要返回的jsp页面，放在orderList当中
        modelMap.addAttribute("orderList", orderList);

        return "admin/orders";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addOrder(ModelMap modelMap){
        List<UserEntity> userList = userRepo.findAll();
        List<BookEntity> bookList = bookRepo.findAll();
        modelMap.addAttribute("userList", userList);
        modelMap.addAttribute("bookList", bookList);
        return "admin/orderOps/addOrder";
    }

    @RequestMapping(value = "/addP", method = RequestMethod.POST)
    public String addOrderPost(@ModelAttribute("order") @Valid OrderEntity order, BindingResult result){
        if(!result.hasErrors()) {
            orderRepo.saveAndFlush(order);
        }
        return "redirect:/admin/orders";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateOrder(@PathVariable("id") int id, ModelMap modelMap){
        OrderEntity order = orderRepo.findOne(id);
        List<UserEntity> userList = userRepo.findAll();
        List<BookEntity> bookList = bookRepo.findAll();

        modelMap.addAttribute("order", order);
        modelMap.addAttribute("userList", userList);
        modelMap.addAttribute("bookList", bookList);

        return "admin/orderOps/updateOrder";
    }

    @RequestMapping(value="/updateP", method = RequestMethod.POST)
    public String updateOrderPost(@ModelAttribute("order") @Valid OrderEntity order, BindingResult result){
        if(!result.hasErrors()){
            if(!order.equals(orderRepo.findOne(order.getOid()))) {
                orderRepo.updateOrder(order.getUserByUid().getUid(), order.getBookByBid().getBid(),
                        order.getQuantity(), order.getOid());
                orderRepo.flush();
            }
        }
        return "redirect:/admin/orders";
    }

    @RequestMapping(value="/delete/{id}")
    public String deleteBlog(@PathVariable("id") int id){
        orderRepo.delete(id);
        orderRepo.flush();
        return "redirect:/admin/orders";
    }

}
