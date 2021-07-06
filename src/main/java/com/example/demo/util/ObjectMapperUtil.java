package com.example.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperUtil {
   private static final ObjectMapper mapper = new ObjectMapper();

   public static ObjectMapper getMapper() {
      return mapper;
   }
}
