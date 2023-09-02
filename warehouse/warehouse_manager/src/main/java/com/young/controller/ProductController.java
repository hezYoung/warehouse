package com.young.controller;

import com.young.page.Page;
import com.young.pojo.*;
import com.young.service.*;
import com.young.utils.TokenUtils;
import com.young.vo.Result;
import com.young.vo.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/product")
@RestController
public class ProductController {
    //注入StoreService
    @Autowired
    private StoreService storeService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    //注入ProductTypeService
    @Autowired
    private ProductTypeService productTypeService;
    //注入SupplyService
    @Autowired
    private SupplyService supplyService;
    //注入PlaceService
    @Autowired
    private PlaceService placeService;
    //注入UnitService
    @Autowired
    private UnitService unitService;
    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 查询所有仓库的url接口/product/store-list
     * <p>
     * 返回值Result对象给客户端响应查询到的List<Store>;
     */
    @RequestMapping("/store-list")
    public Result storeList() {
        //执行业务
        List<Store> storeList = storeService.queryAllStore();
        //响应
        return Result.ok(storeList);
    }

    /**
     * 查询所有品牌的url接口/product/brand-list
     * <p>
     * 返回值Result对象给客户端响应查询到的List<Brand>;
     */
    @RequestMapping("/brand-list")
    public Result brandList() {
        //执行业务
        List<Brand> brandList = brandService.queryAllBrand();
        //响应
        return Result.ok(brandList);
    }

    @RequestMapping("/product-page-list")
    public Result productpage(Page page, Product product) {
        Page productPage = productService.queryProductPage(page, product);
        return Result.ok(productPage);
    }


    /**
     * 查询所有商品分类树的url接口/product/category-tree
     *
     * 返回值Result对象给客户端响应查询到的所有商品分类树List<ProductType>;
     */
    @RequestMapping("/category-tree")
    public Result categoryTree(){
        //执行业务
        List<ProductType> typeTreeList = productTypeService.allProductTypeTree();
        //响应
        return Result.ok(typeTreeList);
    }
    /**
     * 查询所有供应商的url接口/product/supply-list
     *
     * 返回值Result对象给客户端响应查询到的List<Supply>;
     */
    @RequestMapping("/supply-list")
    public Result supplyList(){
        //执行业务
        List<Supply> supplyList = supplyService.queryAllSupply();
        //响应
        return Result.ok(supplyList);
    }

    /**
     * 查询所有产地的url接口/product/place-list
     *
     * 返回值Result对象给客户端响应查询到的List<Place>;
     */
    @RequestMapping("/place-list")
    public Result placeList(){
        //执行业务
        List<Place> placeList = placeService.queryAllPlace();
        //响应
        return Result.ok(placeList);
    }
    /**
     * 查询所有单位的url接口/product/unit-list
     *
     * 返回值Result对象给客户端响应查询到的List<Unit>;
     */
    @RequestMapping("/unit-list")
    public Result unitList(){
        //执行业务
        List<Unit> unitList = unitService.queryAllUnit();
        //响应
        return Result.ok(unitList);
    }
    /**
     * 将配置文件的file.upload-path属性值注入给控制器的uploadPath属性,
     * 其为图片上传到项目的目录路径(类路径classes即resources下的static/img/upload);
     */
    @Value("${file.upload-path}")
    private String uploadPath;

    /**
     * 上传图片的url接口/product/img-upload
     * <p>
     * 参数MultipartFile file对象封装了上传的图片;
     *
     * @CrossOrigin表示该url接口允许跨域请求;
     */
    @CrossOrigin
    @PostMapping("/img-upload")
    public Result uploadimg(MultipartFile file){
        try {
            //拿到图片上传到的目录(类路径classes下的static/img/upload)的File对象
            File uploadDirFile = ResourceUtils.getFile(uploadPath);
            //拿到图片上传到的目录的磁盘路径
            String uploadDirPath = uploadDirFile.getAbsolutePath();
            //拿到图片保存到的磁盘路径
            String fileUploadPath = uploadDirPath + "\\" + file.getOriginalFilename();
            //保存图片
            file.transferTo(new File(fileUploadPath));
            //成功响应
            return Result.ok("图片上传成功！");
        } catch (Exception e) {
            //失败响应
            return Result.err(Result.CODE_ERR_BUSINESS, "图片上传失败！");
        }

    }
    @RequestMapping("/product-add")
    public Result addProduct(@RequestBody Product product,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即添加商品的用户id
        int createBy = currentUser.getUserId();
        product.setCreateBy(createBy);

        //执行业务
        Result result = productService.saveProduct(product);

        //响应
        return result;
    }
    /**
     * 修改商品上下架状态的url接口/product/state-change
     *
     * @RequestBody Product product用于接收并封装请求json数据;
     */
    @RequestMapping("/state-change")
    public Result changeProductState(@RequestBody Product product){
        //执行业务
        Result result = productService.updateProductState(product);
        //响应
        return result;
    }
//删除接口
@RequestMapping("/product-delete/{productId}")
public Result deleteProduct(@PathVariable Integer productId){
    //执行业务
    Result result = productService.delGoodsbyId(Arrays.asList(productId));
    //响应
    return result;
}
//批量删除
@RequestMapping("/product-list-delete")
public Result deleteProductIds(@RequestBody List<Integer> productId){
    //执行业务
    Result result = productService.delGoodsbyId(productId);
    //响应
    return result;
}
//修改
@RequestMapping("/product-update")
public Result updateProduct(@RequestBody Product product,
                            @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){

    //获取当前登录的用户
    CurrentUser currentUser = tokenUtils.getCurrentUser(token);
    //获取当前登录的用户id,即修改商品的用户id
    int updateBy = currentUser.getUserId();
    product.setUpdateBy(updateBy);

    //执行业务
    Result result = productService.updateProduct(product);

    //响应
    return result;
}


}
