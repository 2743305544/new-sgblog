package org.admin.controller;

import com.example.domain.ResponseResult;
import com.example.domain.entry.Menu;
import com.example.service.MenuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult menuList(@RequestParam(value = "menuName",required = false) String menuName,@RequestParam(value = "status",required = false) String status){
        return menuService.menuList(menuName,status);
    }
    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }
    @GetMapping("/{id}")
    public ResponseResult MenuById(@PathVariable("id") Long id){
        return menuService.MenuById(id);
    }
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }
    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId){
        menuService.removeById(menuId);
        return ResponseResult.okResult();
    }
    @GetMapping("/treeselect")
    public ResponseResult treeSelect(){
        return menuService.treeSelect();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselectById(@PathVariable("id") Long id){
        return menuService.roleMenuTreeselectById(id);
    }
}
