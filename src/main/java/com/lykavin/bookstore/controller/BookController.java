package com.lykavin.bookstore.controller;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.repository.BookRepository;
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
@RequestMapping("/admin/books*")
public class BookController {
//
//    @Autowired
//    BookRepository bookRepository;
//
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public String getBooks(ModelMap modelMap){
//        // 查询book中所有记录
//        List<BookEntity> bookList = bookRepository.findAll();
//
//        // 将所有记录传递给要返回的jsp页面，放在bookList当中
//        modelMap.addAttribute("bookList", bookList);
//
//
//        return "admin/books";
//    }
//
//    // get请求，访问添加用户 页面
//    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    public String addBook() {
//
//        return "admin/bookOps/addBook";
//    }
//
//    // post请求，处理添加用户请求，并重定向到用户管理页面
//    @RequestMapping(value = "/addP", method = RequestMethod.POST)
//    public String addBookPost(@ModelAttribute("book") @Valid BookEntity bookEntity, BindingResult result) {
//
//        if(!result.hasErrors()) {
//            bookRepository.saveAndFlush(bookEntity);
//        }
//
//        // 重定向到用户管理页面，方法为 redirect:url
//        return "redirect:/admin/books";
//    }
//
//
//    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
//    public String showBook(@PathVariable("id") Integer bookId, ModelMap modelMap) {
//
//        BookEntity bookEntity = bookRepository.findOne(bookId);
//
//        // 传递给请求页面
//        modelMap.addAttribute("book", bookEntity);
//        return "admin/bookOps/bookDetail";
//    }
//
//    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
//    public String updateUser(@PathVariable("id") Integer bookId, ModelMap modelMap) {
//
//
//        BookEntity bookEntity = bookRepository.findOne(bookId);
//
//        // 传递给请求页面
//        modelMap.addAttribute("book", bookEntity);
//        return "admin/bookOps/updateBook";
//    }
//
//    // 更新用户信息 操作
//    @RequestMapping(value = "/updateP", method = RequestMethod.POST)
//    public String updateBookPost(@ModelAttribute("book") @Valid BookEntity book, BindingResult result) {
//
//        if(!result.hasErrors()) {
//            // 更新用户信息
//            bookRepository.updateBook(book.getTitle(), book.getPrice(),
//                    book.getDescription(), book.getAuthor(), book.getImg(), book.getBid());
//            bookRepository.flush(); // 刷新缓冲区
//        }
//
//        return "redirect:/admin/books";
//    }
//
//    // 删除用户
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
//    public String deleteBook(@PathVariable("id") Integer bookId) {
//
//        // 删除id为userId的用户
//        bookRepository.delete(bookId);
//        // 立即刷新
//        bookRepository.flush();
//        return "redirect:/admin/books";
//    }
}
