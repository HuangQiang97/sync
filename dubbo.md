### swager

**`@Api`**

- 用于类级别，标识一个类是一个 API 资源。

**`@ApiOperation`**

- 用于方法级别，描述一个具体的 API 操作。

**`@ApiParam`**

- 用于方法参数级别，描述一个操作的参数：`f(@ApiParam(value = "ID", required = true) @PathVariable Long id)`

```java
@Api(value = "xxx", tags = "xxx")
@RestController
@RequestMapping("/users")
public class UserController {
    @ApiOperation(value = "xxx", notes = "xxx")
    @GetMapping("/{id}")
    public User getUserById(@ApiParam(value = "xxx", required = true)@PathVariable Long id) {
    }
}
```

**`@ApiModel`**

- 用于模型类级别，描述一个DTO参数模型。

**`@ApiModelProperty`**

- 用于模型类的字段，描述模型的属性。

```java
@ApiModel(value = "xxx", description = "xxx")
public class User {
    @ApiModelProperty(value = "xxx", required = true)
    private Long id;
}
```

**`@ApiResponse` 和 `@ApiResponses`**

- 用于方法级别，描述操作的响应信息。

**`@ApiImplicitParam` 和 `@ApiImplicitParams`**

- 用于描述隐式请求参数。

```java
@ApiResponses(value = {
    @ApiResponse(code = 200, message = "xxx", response = User.class),
    @ApiResponse(code = 404, message = "xxx")
})
@GetMapping("/{id}")
public ResponseEntity<User> getUserById(@PathVariable Long id) {
}

@ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "xxx", required = true, dataType = "long", paramType = "path"),
    @ApiImplicitParam(name = "name", value = "xxx", required = false, dataType = "string", paramType = "query")
})
@GetMapping("/{id}")
public ResponseEntity<User> getUserByIdAndName(@PathVariable Long id, @RequestParam String name) {
}
```

### 注解装配

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
// 作用和@Configuration相同，只适用于标识该配置注解用于主配置类或启动类上
// 通过该注解让EnableMinioClient被装配，使得Import生效
@SpringBootConfiguration
// 其它module中通过添加@EnableMinioClient注解，将手动触发MinoConfiguration的导入
// MinoConfiguration再触发ComponentScan，完成bean的装配
@Import(MinoConfiguration.class)
public @interface EnableMinioClient {
}
```

```java
@ComponentScan("com.hyts.assemble.minio")
// 通过该注解让MinoConfiguration被装配，使得ComponentScan生效
@Configuration
public class MinoConfiguration {
}
```

### Gatway

* https://cloud.tencent.com/developer/article/2135737