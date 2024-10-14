package org.admin.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.example.domain.ResponseResult;
import com.example.domain.dto.CategoryDto;
import com.example.domain.entry.Category;
import com.example.domain.vo.CategoryVo;
import com.example.domain.vo.CategoryVo2;
import com.example.domain.vo.ExcelCategoryVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.service.CategoryService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.WebUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.codec.EncodingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            String format = sdf.format(new Date());
            WebUtils.setDownLoadHeader("分类"+format+".xlsx",response);
            List<Category> list = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(list, ExcelCategoryVo.class);
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出").doWrite(excelCategoryVos);
        } catch (Exception e) {
            response.reset();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
            e.printStackTrace();
        }
    }
    @GetMapping("/list")
    public ResponseResult lists(@RequestParam("pageNum") Integer pageNum ,
                                @RequestParam("pageSize") Integer pageSize,
                                @RequestParam(value = "name",required = false) String name,
                                @RequestParam(value = "status",required = false) String status){
        return categoryService.lists(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult add(@RequestBody CategoryDto categoryDto){
        return categoryService.add(categoryDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategoryDetail(@PathVariable("id") Long id){
        return categoryService.getCategoryDetail(id);
    }
    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryVo2 categoryVo2){
        return categoryService.updateCategory(categoryVo2);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }
}
