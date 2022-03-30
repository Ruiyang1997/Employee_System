package com.ry.employee_system.controller;

import com.ry.employee_system.entity.Emp;
import com.ry.employee_system.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("emp")
@CrossOrigin
@Slf4j
public class EmpController {

    @Autowired
    private EmpService empService;

    @Value("${photo.dir}")
    private String realPath;

    //保存员工信息
    @PostMapping("save")
    public Map<String,Object> save(Emp emp, MultipartFile photo) throws IOException
    {
        log.info("员工信息: [{}]",emp.toString());
        log.info("头像信息: [{}]",photo.getOriginalFilename());
        Map<String,Object> map = new HashMap<>();

        try {
            //头像保存
            //重命名
            String newFileName = UUID.randomUUID().toString()+"."+ FilenameUtils.getExtension(photo.getOriginalFilename());
            photo.transferTo(new File(realPath,newFileName));
            //保存头像地址
            emp.setPath(newFileName);
            //保存员工信息
            empService.save(emp);
            map.put("state",true);
            map.put("msg","员工信息保存成功！");
        } catch (IOException e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","员工信息保存失败！");
        }

        return map;
    }

    //获取员工列表方法
    @GetMapping("findAll")
    public List<Emp> findAll()
    {
        return empService.findAll();
    }

    //删除员工信息
    @GetMapping("delete")
    public Map<String,Object> delete(String id)
    {
        log.info("删除员工的id[{}]",id);
        Map<String,Object> map = new HashMap<>();

        try {
            //删除员工上传头像
            Emp emp = empService.findOne(id);
            File file = new File(realPath,emp.getPath());
            if(file.exists())
            {
                //删除头像
                file.delete();
            }
            empService.delete(id);
            map.put("state",true);
            map.put("msg","删除员工信息成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","删除员工信息失败！");
        }
        return map;
    }
    //查找员工信息
    @GetMapping("findOne")
    public Emp findOne(String id)
    {
        log.info("查询员工信息的id:[{}]",id);
        return empService.findOne(id);
    }

    //修改员工信息
    @PostMapping("update")
    public Map<String,Object> update(Emp emp, MultipartFile photo) throws IOException
    {
        log.info("员工信息: [{}]",emp.toString());
        Map<String,Object> map = new HashMap<>();

        try {
            //头像保存
            //重命名
            if(photo!=null&&photo.getSize()!=0)
            {
                log.info("头像信息: [{}]",photo.getOriginalFilename());
                String newFileName = UUID.randomUUID().toString()+"."+ FilenameUtils.getExtension(photo.getOriginalFilename());
                photo.transferTo(new File(realPath,newFileName));
                //保存头像地址
                emp.setPath(newFileName);
            }
            //保存员工信息
            empService.update(emp);
            map.put("state",true);
            map.put("msg","员工信息保存成功！");
        } catch (IOException e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","员工信息保存失败！");
        }

        return map;
    }


}


