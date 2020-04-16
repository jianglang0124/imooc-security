package com.imooc.web.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTestController {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public  void  setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    @Test
    public  void  whenQuerySuccess() throws Exception{
       mockMvc.perform(get("/user")
                .param("username", "jojo")
               .param("age", "18")
               .param("ageTo", "60")
                .param("xxx", "yyy")
                 .param("size", "15")
                 .param("page", "3")
                 .param("sort", "age,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


    }
    @Test
    public  void  whenQuerySuccess1() throws Exception {
        String contentAsString = mockMvc.perform(get("/user/1")
                // .param("username", "jojo")
                // .param("age", "18").param("ageTo", "60").param("xxx", "yyy")
                // .param("size", "15")
                // .param("page", "3")
                // .param("sort", "age,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

        @Test
        public  void  whenQuerySuccess2() throws Exception{
            String content = "{\"username\":\"Tom\",\"password\":null}";
            String contentAsString1 = mockMvc.perform(post("/user").content(content)
                    // .param("username", "jojo")
                    // .param("age", "18").param("ageTo", "60").param("xxx", "yyy")
                    // .param("size", "15")
                    // .param("page", "3")
                    // .param("sort", "age,desc")
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            System.out.println(contentAsString1);
        }

    @Test
    public  void  whenUpdateSuccess() throws Exception {
        String content = "{\"username\":\"jack\",\"password\":34555}";
        String contentAsString = mockMvc.perform(put("/user/1").content(content)
                // .param("username", "jojo")
                // .param("age", "18").param("ageTo", "60").param("xxx", "yyy")
                // .param("size", "15")
                // .param("page", "3")
                // .param("sort", "age,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    @Test
    public  void  whenDeleteSuccess() throws Exception {
        String contentAsString = mockMvc.perform(delete("/user/1")
                // .param("username", "jojo")
                // .param("age", "18").param("ageTo", "60").param("xxx", "yyy")
                // .param("size", "15")
                // .param("page", "3")
                // .param("sort", "age,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

}
