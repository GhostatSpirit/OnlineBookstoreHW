package com.lykavin.bookstore.controller;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.repository.BookRepository;
import com.lykavin.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.print.Book;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by lykav on 2017/4/23.
 */
@Controller
@RequestMapping("/admin/book")
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBook(Model model, HttpServletRequest request){
        BookEntity book = new BookEntity();
        model.addAttribute("book", book);

        // String rpath = request.getSession().getServletContext().getRealPath("/");

        return "/admin/book/add";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String addBookPost(@ModelAttribute("book")BookEntity book, HttpServletRequest request){

        System.out.println(book.toString());

        bookService.save(book);

        MultipartFile bookImage = book.getBookImage();

        if(!bookImage.isEmpty()) {
            try {
                byte[] bytes = bookImage.getBytes();
                String name = book.getId() + ".png";
                BufferedOutputStream stream = new BufferedOutputStream(

                        // store the book file under the editor directory for test uses
                        // MODIFY THIS LINE OF CODE BEFORE PRODUCTION
                        new FileOutputStream(new File("src/main/resources/static/image/book/" + name))
                );
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/admin/book/list";
    }

    @RequestMapping("/list")
    public String bookList(Model model){
        List<BookEntity> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);

        return "/admin/book/list";
    }

    @RequestMapping("/info")
    public String getBookInfo(@RequestParam("id") Long id, Model model){
        BookEntity book = bookService.findOne(id);
        model.addAttribute("book", book);

        return "/admin/book/info";
    }

    @RequestMapping(value="/update", method = RequestMethod.GET)
    public String updateBook(@RequestParam("id") Long id, Model model){
        BookEntity book = bookService.findOne(id);
        model.addAttribute("book", book);
        return "/admin/book/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String updateBookPost(@ModelAttribute("book") BookEntity book, HttpServletRequest request){
        bookService.save(book);

        MultipartFile bookImage = book.getBookImage();

        if(!bookImage.isEmpty()) {
            try {
                byte[] bytes = bookImage.getBytes();
                String name = book.getId() + ".png";

                // delete the existing book image file
                Files.deleteIfExists(Paths.get("src/main/resources/static/image/book/"+name));

                BufferedOutputStream stream = new BufferedOutputStream(

                        // store the book file under the editor directory for test uses
                        // MODIFY THIS LINE OF CODE BEFORE PRODUCTION
                        new FileOutputStream(new File("src/main/resources/static/image/book/" + name))
                );
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/admin/book/info?id="+book.getId();
    }


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
