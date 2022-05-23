package com.main.springbootcloudtemplatingservice.services;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class TemplatingService {

    private StringBuilder smartContractCode;


    @SneakyThrows
     public String createTemplate(List<String> constructorCode) { //src/main/resources/smartContractCode.sol

        File file = ResourceUtils.getFile("classpath:smartContractCode.sol");
        StringBuffer smartContractCode = new StringBuffer(FileUtils.readFileToString(file, StandardCharsets.UTF_8));



        int index = smartContractCode.indexOf("//push here");
        smartContractCode.insert(index,"user = person(");
        index+=14;
        for(int i = 0; i<constructorCode.size()-1;i++){
              smartContractCode.insert(index,"\"");
              index++;
              smartContractCode.insert(index, constructorCode.get(i));
              index += constructorCode.get(i).length();
              smartContractCode.insert(index,"\",");
              index+=2;
        }
        smartContractCode.insert(index,constructorCode.get(constructorCode.size()-1)+");");
        return smartContractCode.toString();
    }
}
