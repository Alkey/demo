package com.example.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
   private static final ObjectMapper mapper = new ObjectMapper();

   public static ObjectMapper getMapper() {
      return mapper;
   }
}
