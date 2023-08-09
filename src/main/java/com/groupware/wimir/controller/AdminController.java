package com.groupware.wimir.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@RestController
@RequestMapping("/admin")
@SessionAttributes("loginMember")
public class AdminController {


}
