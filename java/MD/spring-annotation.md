# Spring Annotation

## @Scope

```java
@Configuration // 表明该类是个配置类, 容器启动需要加载的类
public class MainConfig {
    /**
     * @Author yangyun
     * @Description:
     *  Scope: 表示加载到容器中实例对象的作用域
     *      ConfigurableBeanFactory.SCOPE_PROTOTYPE: 表示多实例, 每次都会新创建一个实例
     *          多实例模式下, 容器启动并不会加载实例, 只会在使用的时候才创建
     *      ConfigurableBeanFactory.SCOPE_SINGLETON: 默认单列
     *          单列模式, 在容器启动的时候就会创建实例对象加载到容器中, 使用的时候直接使用已经创建好的实例对象
     *      WebApplicationContext.SCOPE_REQUEST: 同意请求域只创建一个
     *      WebApplicationContext.SCOPE_SESSION: 同一个session域只创建一个
     * @Date 2020/1/17 10:32
     * @Param []
     * @returnm com.yangyun.bean.Person
     **/
    @Scope(value = "prototype")
    @Bean // 表明方法生成的 Bean 由 Spring Container 来管理
    @Lazy // 默认 true, 该注解是配合单列模式下使用, 表示容器启动不会加载对象, 只有在第一次使用的时候加载
    public Person person (){
        System.out.println("创建实例....");
        return new Person("张三", 25);
    }
}
```

## @Conditional

根据指定条件, 判断是否满足来决定是否将指定 bean 加载到容器中

```java
// 该注解可以作用在方法上, 也可以作用在类上
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {

	/**
	 * @Conditional(Condition) 类型的对象作为条件判断, 需要实现 Condition 接口
	 * 
	 */
	Class<? extends Condition>[] value();
}

public class MyConditional implements Condition {
	/**
	 * ConditionContext: 判断添加条件能使用的上下文环境
	 * AnnotatedTypeMetadata:当前标注了 Conditional 注解的注释信息
	 */
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 获取当前环境信息
        Environment environment = context.getEnvironment();
        // 获取资源信息
        ResourceLoader resourceLoader = context.getResourceLoader();
        // 获取 bean 定义的注册信息
        BeanDefinitionRegistry registry = context.getRegistry();
        return false;
    }
}
```

## 注册组件

1. 包扫描(@ComponentScans(@ComponentScan))+组件标注注解(@Controller/@Service/@Repository/@Component)<加载自己的类>
2. @Bean(加载第三方组件)
3. @Import(快速给容器导入一个组件)
   1. 作用在类上, @Import(Class[]), 可以直接通过XXX.class 的方式导入指定的组件
   2. 通过实现ImportSelector 接口, 自定义导入组件
   3. ImportBeanDefinitionRegistrar 类似 ImportSelector 
   4. Spring 提供 FactoryBean
      1. 默认获取到的工厂bean调用的 getObject 获取的对象
      2. 获取工厂bean 本身, 需要给 id 前面加 & 前缀

```java
@Configuration
@Import({Person.class, MyImportSelector.class}) // 直接要导入容器中的组件, id 默认全类名
public class MainConfig {
    
}

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // AnnotationMetadata: 标注了被修饰的类的全部注解信息
        // 返回值为全类名字符串
        return new String[0];
    }
}
```

## Bean的生命周期

create--->init--->destroy

```java
@Configuration
@ComponentScan("com.yangyun.bean")
public class BeanConfiCycle {

    /**
     * 1. 通过@Bean 容器管理bean的生命周期, 我们可以指定初始化和销毁方法
     * 构造对象
     *		单实例: 在容器启动的时候加载实例,初始化实例到容器中, 容器关闭的时候销毁实例
     *		多实例: 容器只会在使用实例的时候才创建并初始化实例, 并且关闭容器的, 并不会调用销毁方法
     * 2. 通过 bean 实现 InitializingBean 这个接口, 重写 afterPropertiesSet 它会在设值完所有属
     * 	  后调用
     *    通过实现 DisposableBean 完成销毁方法
     * 3. 通过 JSR250 规范提供的 @PostConstruct 和 @PreDestroy 注解
     *		@PostConstruct: 在无参构造方法调用完成后调用
     *		@PreDestroy: 在容器销毁后调用
     * 4. BeanPostProcessor: bean 前后置处理器
     *		postProcessBeforeInitialization: 在初始化实例之前调用
     *		postProcessAfterInitialization: 在初始化实例之后调用
     * 5. Spring 底层对 BeanPostProcessor 的应用
     * 		Bean 赋值, 注入其他组件, @Autowired, 生命周期注解功能, @Async, 都是通过
     *		BeanPostProcessor(在Bean 创建初始化完成之前后调用)及其子类实现
     * 6. 自定义组件使用 Spring 底层的组件, 只需要实现 XXXXAware, 在创建对象的时候会调用规定的方法
     *	  进行注入; 每个 XXXAware 都有与之对应的 XXXAwareProcessor 后置处理器
     */
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car(){

        return new Car();
    }
}

public class Car {

    public Car(){
        System.out.println("Car construct...");
    }

    public void destroy(){
        System.out.println("Car destroy...");
    }

    public void init (){
        System.out.println("Car init ...");
    }
}
```

```java
@Component
public class BusDisposableBean implements DisposableBean, InitializingBean {

	// 设置bean属性
    public BusDisposableBean(){
        System.out.println("BusDisposableBean construct....");
    }

	// 销毁方法
    @Override
    public void destroy() throws Exception {
        System.out.println("BusDisposableBean destroy...");
    }
	// 初始化方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("BusDisposableBean  afterPropertiesSet");
    }
}
```

## AOP(注解版)

### Aspect Class

```java
package com.yangyun.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * @ClassName LogAspect
 * @Description:
 * @Author 86155
 * @Date 2020/1/19 10:04
 * @Version 1.0
 **/
@Aspect // 标注为切面类
public class LogAspect {

    /**
     * @Author yangyun
     * @Description:  公共的切入点表达式; 表示在方法执行时触发CalculateService下任意方法, 任意参数, 任意返回值类型
     * @Date 2020/1/19 10:14
     * @Param [point]
     * @returnm void
     **/
    @Pointcut("execution(public * com.yangyun.aop.CalculateService.*(..))")
    public void pointCut(){

    }


    @Before("pointCut()")
    public void logStart(JoinPoint point){
        Object[] args = point.getArgs();
        System.out.println("@Before执行方法: " + point.getSignature().getName() + ", 参数{"+ Arrays.asList(args) +"}");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint point){
        System.out.println("@After执行方法: " + point.getSignature().getName());
    }

    // result 为指定的接收方法的返回值
    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturning(JoinPoint point, Object result){

    }

    // exception 目标方法出现异常时接收
    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logThrowing(JoinPoint point, Exception exception){
        System.out.println("@AfterThrowing"+point.getSignature().getName()+"异常。。。异常信息：{"+exception+"}");
    }
}

```

### Service

```java
package com.yangyun.aop;

import org.springframework.stereotype.Service;

/**
 * @ClassName CalculateService
 * @Description:
 * @Author 86155
 * @Date 2020/1/19 10:03
 * @Version 1.0
 **/
@Service
public class CalculateService {

    public int div(int i, int j){
        System.out.println("processing target method..");
        return i/j;
    }
}
```

### Configuration

```java
package com.yangyun.config;

import com.yangyun.aop.CalculateService;
import com.yangyun.aop.LogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName MainAopConfig
 * @Description:
 * @Author 86155
 * @Date 2020/1/19 10:02
 * @Version 1.0
 **/
@EnableAspectJAutoProxy // 开启基于注解版的aop功能
@Configuration
public class MainAopConfig {

    @Bean
    public CalculateService calculateService (){

        return new CalculateService();
    }

    @Bean
    public LogAspect logAspect (){
        return new LogAspect();
    }

}
```

### aop运行流程

#### 分析

1. 开启基于注解的aop功能需要@EnableAspectJAutoProxy

   1. @Import(AspectJAutoProxyRegistrar.class) 给容器中导入AspectJAutoProxyRegistrar, 利用AspectJAutoProxyRegistrar自定义给容器中注册 bean(BeanDefinitionRegistry)

      ```java
      class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar(
      	AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
      	public void registerBeanDefinitions(
              // 如果必要就注册 AspectJAnnotationAutoProxyCreator
          	AopConfigUtils.
              	registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);    
          }
      }
      ```

   2. 给容器中注册 AnnotationAwareAspectJAutoProxyCreator

2. AnnotationAwareAspectJAutoProxyCreator, 实际上 是一个bean后置处理器

   1. internalAutowiredAnnotationProcessor = AnnotationAwareAspectJAutoProxyCreator --> AspectJAwareAdvisorAutoProxyCreator --> AbstractAdvisorAutoProxyCreator --> AbstractAutoProxyCreator --> implements SmartInstantiationAwareBeanPostProcessorg

#### 流程

1. 根据配置类创建 IOC 容器

   ```java
   AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainAopConfig.class);
   ```

2.  注册配置类, 刷新容器

   ```java
   public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
       this();
       register(annotatedClasses);
       refresh();
   }
   ```

3. registerBeanPostProcessors(beanFactory); 在所有应用bean创建之前实例化并注册BeanPostProcessor

   1. ```java
      public static void registerBeanPostProcessors(
      	ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {
          // 获取ioc容器已经定义了的需要创建对象的 BeanPostProcessor
      	String[] postProcessorNames = 
              beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
          // 添加其他 BeanPostProcessor
          beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));
          // 获取 BeanPostProcessor 对象, 并保存到容器中
          BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
          // 优先注册实现了 PriorityOrdered 接口的 BeanPostProcessor
          sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
          registerBeanPostProcessors(beanFactory, priorityOrderedPostProcessors);
          // 再注册实现了 Ordered 接口的 BeanPostProcessor
          sortPostProcessors(orderedPostProcessors, beanFactory);
          registerBeanPostProcessors(beanFactory, orderedPostProcessors);
          // 然后注册所有常规的 BeanPostProcessor
          registerBeanPostProcessors(beanFactory, nonOrderedPostProcessors);
      }
      public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
          return doGetBean(name, requiredType, null, false);
      }

      protected <T> T doGetBean(final String name, 
      	@Nullable final Class<T> requiredType,
          @Nullable final Object[] args, boolean typeCheckOnly) throws BeansException {
          /**
           * 从缓存中获取根据 BeanPostProcessor 名字获取对应的 BeanPostProcessor, 因为是第一次创
           * 建, 所以 sharedInstance=null
           */
          Object sharedInstance = getSingleton(beanName);
          
          // Create bean instance.
          // 实际在注册获取 BeanPostProcessor 的时候是在创建 BeanPOStProcessor 并保存
          if (mbd.isSingleton()) {
              sharedInstance = getSingleton(beanName, () -> {
                  try {
                      return createBean(beanName, mbd, args);
                  }
                  catch (BeansException ex) {
                      destroySingleton(beanName);
                      throw ex;
                  }
              });
              bean = getObjectForBeanInstance(sharedInstance, name,beanName, mbd);
          }
      }

      protected Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) throws BeanCreationException {
          // 执行创建
      	Object beanInstance = doCreateBean(beanName, mbdToUse, args);
          return beanInstance;
      }

      protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final @Nullable Object[] args) throws BeanCreationException {
      	BeanWrapper instanceWrapper = null;
          // 如果是 singleton 移除(其实就更新, 后面会重新添加到容器)
          if (mbd.isSingleton()) {
              instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
          }
          // 创建 Bean
          if (instanceWrapper == null) {
              instanceWrapper = createBeanInstance(beanName, mbd, args);
          }
          // 包装 bean
          final Object bean = instanceWrapper.getWrappedInstance();
          
          Object exposedObject = bean;
          try {
              // 属性赋值
              populateBean(beanName, mbd, instanceWrapper);
              // 初始化 bean, BeanPostProcessor 是在bean的初始化前后执行
              exposedObject = initializeBean(beanName, exposedObject, mbd);
          }
      }

      protected Object initializeBean(final String beanName, final Object bean, @Nullable RootBeanDefinition mbd) {
          /**
           * 判断bean是否为 Aware 接口的实现, 如果是会执行 Aware 接口方法的回调
           */
      	invokeAwareMethods(beanName, bean);
          
          Object wrappedBean = bean;
          if (mbd == null || !mbd.isSynthetic()) {
              // 应用所有后置处理器 postProcessBeforeInitialization()
              wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
          }
          try {
              // 执行自定义的初始化方法
              invokeInitMethods(beanName, wrappedBean, mbd);
          }
          
          if (mbd == null || !mbd.isSynthetic()) {
              // 应用所有后置处理器 postProcessAfterInitialization()
              wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
          }
      }
      ```

   2. BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator) 创建成功
   3. registerBeanPostProcessors; 将所有BeanPostProcessor添加到BeanFactory中

   ================以上是创建和注册 AnnotationAwareAspectJAutoProxyCreator ==================

   1. finishBeanFactoryInitialization(beanFactory); 完成BeanFactory 初始化工作, 创建剩下的单列 bean

   ```java
   if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit())// 满足这些条件的单实例bean
   ```

   1. preInstantiateSingletons; 遍历获取所有的bean, 依次创建 bean  

      getBean() --> doGetBean() --> getSingleton()

   2. 创建 bean

      1. 先从缓存中获取; Object singletonObject = this.singletonObjects.get(beanName); 付过能获取就返回, 没有就创建缓存返回

      2. createBean(); 创建bean; 

         AnnotationAwareAspectJAutoProxyCreator 会在任何 bean 创建之前，通过

         AnnotationAwareAspectJAutoProxyCreator  返回 Bean 的代理对象

         【BeanPostProcessor 是在 bean 创建完， 初始化前后调用】

         【InstantiationAwareBeanPostProcessor 是在创建 bean 实例对象之前先尝试调用后置处理器返回代理对象】

         ```java
         RootBeanDefinition mbdToUse = mbd; // bean的定义信息
         // 希望后置处理器在此能返回一个代理对象, 如果能返回就使用, 不能返回在创建
         Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
         ```

         ```java
         protected Object resolveBeforeInstantiation(String beanName, RootBeanDefinition mbd) {
             Object bean = null;
             if (!Boolean.FALSE.equals(mbd.beforeInstantiationResolved)) {
                 // Make sure bean class is actually resolved at this point.
                 if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
                     Class<?> targetType = determineTargetType(beanName, mbd);
                     if (targetType != null) {
                         /**
                          * 获取所有 BeanPostProcessor, 
                           * 如果是 InstantiationAwareBeanPostProcessor
                           * 就执行 postProcessBeforeInstantiation() 
                          */
                         bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
                         if (bean != null) {
                             // 如果后置处理器不为null
                             // 执行所有后置处理器 postProcessAfterInitialization() 
                             bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
                         }
                     }
                 }
                 mbd.beforeInstantiationResolved = (bean != null);
             }
             return bean;
         }
         ```

      3. Object beanInstance = doCreateBean(beanName, mbdToUse, args); 真正去创建一个 bean 实例， 流程和上面  doCreateBean 一致

5. AnnotationAwareAspectJAutoProxyCreator 【InstantiationAwareBeanPostProcessor 】作用

   1. 在每个 Bean 创建之前， 调用 postProcessBeforeInstantiation()

      1. 判断当前 bean 是否在 advisedBeans（保存了所有需要增强 bean） 中

         ```java
         this.advisedBeans.containsKey(cacheKey)
         ```

      2. 判断当前 bean 是否是基础类型 Advice、Pointcut、Advisor、AopInfrastructureBean 或者是切面（是否使用@Aspect）

         ```java
         isInfrastructureClass(beanClass)
         protected boolean isInfrastructureClass(Class<?> beanClass) {
             boolean retVal = Advice.class.isAssignableFrom(beanClass) ||
             Pointcut.class.isAssignableFrom(beanClass) ||
             Advisor.class.isAssignableFrom(beanClass) ||
             AopInfrastructureBean.class.isAssignableFrom(beanClass);
             if (retVal && logger.isTraceEnabled()) {
                 logger.trace("Did not attempt to auto-proxy infrastructure class [" + beanClass.getName() + "]");
             }
             return retVal;
         }
         protected boolean isInfrastructureClass(Class<?> beanClass) {
             return (super.isInfrastructureClass(beanClass) ||
                 (this.aspectJAdvisorFactory != null && 
                  this.aspectJAdvisorFactory.isAspect(beanClass)));
         }
         public boolean isAspect(Class<?> clazz) {
             return (hasAspectAnnotation(clazz) && !compiledByAjc(clazz));
         }
         ```

      3. 是否需要跳过当前 bean

         ```java
         shouldSkip(beanClass, beanName)
         protected boolean shouldSkip(Class<?> beanClass, String beanName) {
             // TODO: Consider optimization by caching the list of the aspect names
             // 获取所有候选的增强器(切面里面的通知方法)
             // 包装后 List<Advisor>, 
             // 每一个增强器都是 InstantiationModelAwarePointcutAdvisor
             List<Advisor> candidateAdvisors = findCandidateAdvisors();
             for (Advisor advisor : candidateAdvisors) {
                 // 判断是否为 AspectJPointcutAdvisor 类型, 是返回 true
                 if (advisor instanceof AspectJPointcutAdvisor &&
                     ((AspectJPointcutAdvisor) 
                      advisor).getAspectName().equals(beanName)) {
                     return true;
                 }
             }
             // super.shouldSkip 永远返回false
             return super.shouldSkip(beanClass, beanName);
         }
         ```

         ![image/aop.jpg](F:\git\study\java\MD\image\aop.jpg)

   2. 在每个 Bean 创建之后， 调用 postProcessAfterInitialization()

      ```java
      // 包装bean 如果需要的情况
      return wrapIfNecessary(bean, beanName, cacheKey);

      protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
          if (StringUtils.hasLength(beanName) && 
              this.targetSourcedBeans.contains(beanName)) {
              return bean;
          }
          if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
              return bean;
          }
          if (isInfrastructureClass(bean.getClass()) || shouldSkip(bean.getClass(), 
             beanName)) {
              this.advisedBeans.put(cacheKey, Boolean.FALSE);
              return bean;
          }
          // 获取当前bean 的所有增强器(通知方法), 获取能在当前bean使用的增强器(通知方法)
          // 找到那些方法是需要切入到当前bean方法的
          // 然后给增强器排序
          Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), 
                                          beanName, null);
          if (specificInterceptors != DO_NOT_PROXY) {
              // 保存当前bean 到缓存中
              this.advisedBeans.put(cacheKey, Boolean.TRUE);
              // 创建当前bean代理对象, 如果当前需要增强(如果包含增强器)
              Object proxy = createProxy(
                  bean.getClass(), beanName, specificInterceptors, new 
                  	SingletonTargetSource(bean));
              this.proxyTypes.put(cacheKey, proxy.getClass());
              return proxy;
          }

          this.advisedBeans.put(cacheKey, Boolean.FALSE);
          return bean;
      }

      protected Object createProxy(Class<?> beanClass, @Nullable String beanName,
      	@Nullable Object[] specificInterceptors, TargetSource targetSource) {
          // 创建代理工厂
          ProxyFactory proxyFactory = new ProxyFactory();
          /// 获取所有增强器
          Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
          // 定制代理工厂
          customizeProxyFactory(proxyFactory);
          // 获取代理对象
          return proxyFactory.getProxy(getProxyClassLoader());
      }

      public Object getProxy(@Nullable ClassLoader classLoader) {
          return createAopProxy().getProxy(classLoader);
      }
      // 创建Aop代理
      protected final synchronized AopProxy createAopProxy() {
          if (!this.active) {
              activate();
          }
          return getAopProxyFactory().createAopProxy(this);
      }

      // 创建aop 代理; spring自动根据匹配动态创建
      public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
          if (config.isOptimize() || config.isProxyTargetClass() || 
              hasNoUserSuppliedProxyInterfaces(config)) {
              Class<?> targetClass = config.getTargetClass();
              if (targetClass == null) {
                  throw new AopConfigException("TargetSource cannot determine target 
                  	class: " +"Either an interface or a target is required for proxy 
                      creation.");
              }
              if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
                  // jdk 动态代理
                  return new JdkDynamicAopProxy(config);
              }
              // cglib 动态代理
              return new ObjenesisCglibAopProxy(config);
          }
          else {
              return new JdkDynamicAopProxy(config);
          }
      }
      // 最终会返回一个 cglib增强的代理对象, 以后容器中获取到的就是这个组件的代理对象, 执行目标方法的时
      // 候, 代理对象就执行通知方法流程
      return wrapIfNecessary(bean, beanName, cacheKey);                                         
      ```

   3. 目标方法执行

      容器中保存了组件的代理对象(cglib 增强后的对象), 这个对象里面保存了详细信息(如: 增强器, 目标对象..)

      1. ```java
         // 容器返回的为代理对象
         CalculateService bean = context.getBean(CalculateService.class);
         // 执行目标方法
         bean.div(1, 1);
         ```

      2. 执行目标方法之前会先进入 

         ```java
         CglibAopProxy.intercept()// 拦截目标方法的执行
         public Object intercept(Object proxy, Method method, Object[] args, MethodProxy 
         	methodProxy) throws Throwable {
             // 1. 根据 ProxyFactory 获取目标方法的拦截器链
             List<Object> chain =
                 this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, 
                 targetClass);
             
             // 2. 如果拦截器链为空, 就直接调用目标方法
             if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
             	Object[] argsToUse = AopProxyUtils.adaptArgumentsIfNecessary(method, 
                                      args);
                 retVal = methodProxy.invoke(target, argsToUse);
             } else {
                 // 3. 如果有拦截器链, 把需要执行的目标对象, 目标方法, 参数和拦截器链等信息传入创建
                 // 一个 CglibMethodInvocation 对象, 并调用
                 retVal = new CglibMethodInvocation(proxy, target, method, args, 
                          	targetClass, chain, methodProxy).proceed();
             }

         }
         ```

         ```java
         // 2.1 拦截器链获取, 先从缓存中获取, 如果没有会使用 AdvisorChainFactory 根据 
         // ProxyFactory 来获取并缓存
         public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, 
         	@Nullable Class<?> targetClass) {
             MethodCacheKey cacheKey = new MethodCacheKey(method);
             List<Object> cached = this.methodCache.get(cacheKey);
             if (cached == null) {
                 cached = 
                   this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(
                   this, method, targetClass);
                 this.methodCache.put(cacheKey, cached);
             }
             return cached;
         }
         // 获取连接器链排序后返回
         public List<Object> getInterceptorsAndDynamicInterceptionAdvice(
             Advised config, Method method, @Nullable Class<?> targetClass) {
          	AdvisorAdapterRegistry registry =
                 GlobalAdvisorAdapterRegistry.getInstance();
                 Advisor[] advisors = config.getAdvisors();
             // 连接器链,里面保存了
             // 一个默认的ExposeInvocationInterceptor, 还有其他所有通知方法
             List<Object> interceptorList = new ArrayList<>(advisors.length);   
             for (Advisor advisor : advisors) {
                 // 遍历所有增强器, 包装成 MethodInterceptor 返回
             	MethodInterceptor[] interceptors = registry.getInterceptors(advisor);   
             }
             return interceptorList;
         }

         public MethodInterceptor[] getInterceptors(Advisor advisor) throws 
             UnknownAdviceTypeException {
             List<MethodInterceptor> interceptors = new ArrayList<>(3);
             Advice advice = advisor.getAdvice();
             // 如果实现了 MethodInterceptor 直接添加到List中
             if (advice instanceof MethodInterceptor) {
                 interceptors.add((MethodInterceptor) advice);
             }
             
             // 如果没有实现MethodInterceptor, 会通过 AdvisorAdapter 进行包装成 
             // AdvisorAdapter
             for (AdvisorAdapter adapter : this.adapters) {
                 if (adapter.supportsAdvice(advice)) {
                     interceptors.add(adapter.getInterceptor(advisor));
                 }
             }
             if (interceptors.isEmpty()) {
                 throw new UnknownAdviceTypeException(advisor.getAdvice());
             }
             return interceptors.toArray(new MethodInterceptor[0]);
         }
         ```

         ![](F:\git\study\java\MD\image\1579504152(1).jpg)

      3. 拦截器链的执行

         连接器链(将通知方法包装成增强器, 然后在执行目标方法的时候再包装成连接器<MethodInterceptor>)

         ![](F:\git\study\java\MD\image\1579576200(1).png)

         ① ExposeInvocationInterceptor(ExposeInvocationInterceptor): 默认拦截器

         ② com.yangyun.aop.LogAspect.logThrowing(AspectJAfterThrowingAdvice)

         ③ com.yangyun.aop.LogAspect.logReturning(AspectJAfterReturningAdvice)

         ④ com.yangyun.aop.LogAspect.logEnd(AspectJAfterAdvice)

         ⑤ com.yangyun.aop.LogAspect.logStart(AspectJMethodBeforeAdvice) 

         ​     <MethodBeforeAdviceInterceptor>

         ![](F:\git\study\java\MD\image\1579577594(1).jpg)

         ```java
         // CglibMethodInvocation..proceed(); 循环调用
         public Object proceed() throws Throwable {
             // currentInterceptorIndex 当前连接器链索引,用来记录, 初始值为 -1
         	if (this.currentInterceptorIndex == 
         		this.interceptorsAndDynamicMethodMatchers.size() - 1) {
                 return invokeJoinpoint();
             }
             // 如果有List不为空, 每次执行 currentInterceptorIndex 自增 1;
             // 从第一个开始执行 ++this.currentInterceptorIndex = 0;
             Object interceptorOrInterceptionAdvice = 
                 this.interceptorsAndDynamicMethodMatchers.
                 get(++this.currentInterceptorIndex);
             // 此处并不匹配
             if (interceptorOrInterceptionAdvice instanceof 
                 InterceptorAndDynamicMethodMatcher) {
                 InterceptorAndDynamicMethodMatcher dm =
                     (InterceptorAndDynamicMethodMatcher) 
                     interceptorOrInterceptionAdvice;
                 Class<?> targetClass = (this.targetClass != null ? this.targetClass : 
                 	this.method.getDeclaringClass());
                 if (dm.methodMatcher.matches(this.method, targetClass, this.arguments)) 
                 {
                     return dm.interceptor.invoke(this);
                 }
                 else {
                     return proceed();
                 }
             }
             else {
                 // 连接器链调用
                 return ((MethodInterceptor) 
                     interceptorOrInterceptionAdvice).invoke(this);
             }
         }
         // 第一个为默认拦截器 ExposeInvocationInterceptor
         // 从共享变量中获取
         private static final ThreadLocal<MethodInvocation> invocation =
             new NamedThreadLocal<>("Current AOP method invocation");

         public Object invoke(MethodInvocation mi) throws Throwable {
             MethodInvocation oldInvocation = invocation.get();
             invocation.set(mi);
             try {
                 return mi.proceed();
             }
             finally {
                 invocation.set(oldInvocation);
             }
         }

         // 第二个执行 AspectJAfterThrowingAdvice
         public Object invoke(MethodInvocation mi) throws Throwable {
             try {
                 return mi.proceed();
             }
             catch (Throwable ex) {
                 if (shouldInvokeOnThrowing(ex)) {
                     invokeAdviceMethod(getJoinPointMatch(), null, ex);
                 }
                 throw ex;
             }
         }

         // 第三个执行 AspectJAfterReturningAdvice
         public Object invoke(MethodInvocation mi) throws Throwable {
             Object retVal = mi.proceed();
             this.advice.afterReturning(retVal, mi.getMethod(), mi.getArguments(), 
             	mi.getThis());
             return retVal;
         }

         // 第四个 AspectJAfterAdvice
         public Object invoke(MethodInvocation mi) throws Throwable {
             try {
                 return mi.proceed();
             }
             finally {
                 invokeAdviceMethod(getJoinPointMatch(), null, null);
             }
         }

         // 第五个 AspectJMethodBeforeAdvice --> MethodBeforeAdviceInterceptor
         public Object invoke(MethodInvocation mi) throws Throwable {
             this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
             return mi.proceed();
         }

         // 所有拦截器执行完 currentInterceptorIndex = 4
         if (this.currentInterceptorIndex == 
             this.interceptorsAndDynamicMethodMatchers.size() - 1) {
             // 执行该方法
             return invokeJoinpoint();
         }

         protected Object invokeJoinpoint() throws Throwable {
             if (this.methodProxy != null) {
                 return this.methodProxy.invoke(this.target, this.arguments);
             }
             else {
                 return super.invokeJoinpoint();
             }
         }
         ```

         ![](F:\git\study\java\MD\image\1579588470(1).png)

         #### 小总结

         1. @EnableAspectJAutoProxy 开启AOP 功能
         2. @EnableAspectJAutoProxy 会给容器注册组件 AnnotationAwareAspectJAutoProxyCreator 后置处理器
         3. 容器创建
            1. registerBeanPostProcessors（）注册后置处理器；创建AnnotationAwareAspectJAutoProxyCreator对象
            2. finishBeanFactoryInitialization（）初始化剩下的单实例bean
               1. 创建业务逻辑组件和切面组件
               2. AnnotationAwareAspectJAutoProxyCreator 拦截组件的创建
               3. 组件创建完成之后, 判断组件是否需要增强(针对通知方法), 将通知方法包装成增强器(Advisor), 给业务组件创建一个代理对象(cglib)
            3. 执行目标方法
               1. 容器获取代理对象并执行目标方法
               2. CglibAopProxy.intercept()
                  1. 得到目标方法连接器链(增强器包装成MetodInterceptor)
                  2. 利用连接器的链式机制, 依次执行拦截器
                  3. 效果
                     1. 正常执行：前置通知--》目标方法--》后置通知--》返回通知
                     2. 出现异常：前置通知--》目标方法--》后置通知--》异常通知

## 声明式事务

### 代码

```java
package com.yangyun.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @ClassName MainConfigTx
 * @Description: 声明式事务
 * @Author 86155
 * @Date 2020/1/21 20:56
 * @Version 1.0
 **/
@EnableTransactionManagement
@ComponentScan("com.yangyun.tx")
@Configuration
public class MainConfigTx {

    @Bean
    public DataSource dataSource () throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://47.107.172.70:3305/test");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    // 注册事务管理器
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        // 事务管理器处理需要管理的数据源
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;
    }
}

```

```java
package com.yangyun.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserDao
 * @Description:
 * @Author 86155
 * @Date 2020/1/21 21:02
 * @Version 1.0
 **/
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertUser(User user){
        String sql = "insert into tbl_user(username, age) values(?, ?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getAge());
    }
}

```

```java
package com.yangyun.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName UserService
 * @Description:
 * @Author 86155
 * @Date 2020/1/21 21:02
 * @Version 1.0
 **/
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void insertUser(User user){
        userDao.insertUser(user);
        System.out.println("新增成功...");
//        int a = 10/0;
    }
}

```

```java
package com.yangyun.test;

import com.yangyun.tx.MainConfigTx;
import com.yangyun.tx.User;
import com.yangyun.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

/**
 * @ClassName TxTest
 * @Description:
 * @Author 86155
 * @Date 2020/1/21 21:37
 * @Version 1.0
 **/
public class TxTest {

    @Test
    public void test(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigTx.class);
        UserService userService = context.getBean(UserService.class);

        User user = new User();
        user.setUserName(UUID.randomUUID().toString().substring(0, 5)).setAge(15);

        userService.insertUser(user);
    }
}
```

### 原理

1. @EnableTransactionManagement 开启声明式事务

   通过 @EnableTransactionManagement 往容器中导入 @Import(TransactionManagementConfigurationSelector.class) 组件

2. ```java
   public class TransactionManagementConfigurationSelector extends 
   AdviceModeImportSelector<EnableTransactionManagement> {
       // 导入需要组件 EnableTransactionManagement
   	protected String[] selectImports(AdviceMode adviceMode) {
   		switch (adviceMode) {
   			case PROXY:
   				return new String[] {AutoProxyRegistrar.class.getName(),
   						ProxyTransactionManagementConfiguration.class.getName()};
   			case ASPECTJ:
   				return new String[] {determineTransactionAspectClass()};
   			default:
   				return null;
   		}
   	}
   }
   // EnableTransactionManagement 默认使用的就是 AdviceMode.PROXY
   public @interface EnableTransactionManagement {
       // true: 指定是否创建基于子类的(cglib)代理
       // false: 基于标准的java接口的代理
       // 并且默认值只有哦在AdviceMode.PROXY 才适用
       boolean proxyTargetClass() default false;
       AdviceMode mode() default AdviceMode.PROXY;
   }
   ```

3. AutoProxyRegistrar 自动代理注册, 根据给定的配置注册信息, 注册 InfrastructureAdvisorAutoProxyCreator

   InfrastructureAdvisorAutoProxyCreator --> AbstractAdvisorAutoProxyCreator --> AbstractAutoProxyCreator --> SmartInstantiationAwareBeanPostProcessor;

   可以知道 InfrastructureAdvisorAutoProxyCreator 属于一个后置处理器

   ```java
   public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
   	boolean candidateFound = false;
       // 获取配置类上注解信息
       Set<String> annoTypes = importingClassMetadata.getAnnotationTypes();
       for (String annoType : annoTypes) {
           // 获取注解中属性信息
           AnnotationAttributes candidate = 
               AnnotationConfigUtils.attributesFor(importingClassMetadata, annoType);
           if (candidate == null) {
               continue;
           }
           Object mode = candidate.get("mode");
           Object proxyTargetClass = candidate.get("proxyTargetClass");
           if (mode != null && proxyTargetClass != null && AdviceMode.class == 
               mode.getClass() && Boolean.class == proxyTargetClass.getClass()) {
               candidateFound = true;
               if (mode == AdviceMode.PROXY) {
                   // 注册自动代理创建者 
                   // @EnableTransactionManagement 的作用 			
                   // ①InfrastructureAdvisorAutoProxyCreator 
                   AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
                   if ((Boolean) proxyTargetClass) {
   					AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
                       return;
                   }
               }
           }
       }
   }
   ```

   InfrastructureAdvisorAutoProxyCreator后置处理器, 在bean 创建前后执行

   此处逻辑可以参考[Spring Aop 流程](https://blog.csdn.net/yangyun901222/article/details/104065485)

   ```java
   public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
       Object cacheKey = getCacheKey(beanClass, beanName);

       if (!StringUtils.hasLength(beanName) || 
           !this.targetSourcedBeans.contains(beanName)) {
           if (this.advisedBeans.containsKey(cacheKey)) {
               return null;
           }
           if (isInfrastructureClass(beanClass) || shouldSkip(beanClass, beanName)) {
               this.advisedBeans.put(cacheKey, Boolean.FALSE);
               return null;
           }
       }

       TargetSource targetSource = getCustomTargetSource(beanClass, beanName);
       if (targetSource != null) {
           if (StringUtils.hasLength(beanName)) {
               this.targetSourcedBeans.add(beanName);
           }
           Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(beanClass, 
                          				    beanName, targetSource);
           Object proxy = createProxy(beanClass, beanName, specificInterceptors, 
                                      targetSource);
           this.proxyTypes.put(cacheKey, proxy.getClass());
           return proxy;
       }
       return null;
   }

   public Object postProcessAfterInitialization(@Nullable Object bean, String beanName) {
       if (bean != null) {
           Object cacheKey = getCacheKey(bean.getClass(), beanName);
           if (this.earlyProxyReferences.remove(cacheKey) != bean) {
               return wrapIfNecessary(bean, beanName, cacheKey);
           }
       }
       return bean;
   }
   ```

4. ProxyTransactionManagementConfiguration spring事务管理器

   ```java
   @Configuration
   public class ProxyTransactionManagementConfiguration extends AbstractTransactionManagementConfiguration {
   	@Bean(name = TransactionManagementConfigUtils.TRANSACTION_ADVISOR_BEAN_NAME)
   	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   	public BeanFactoryTransactionAttributeSourceAdvisor transactionAdvisor() {
   		BeanFactoryTransactionAttributeSourceAdvisor advisor = new 
               BeanFactoryTransactionAttributeSourceAdvisor();
   		advisor.setTransactionAttributeSource(transactionAttributeSource());
   		advisor.setAdvice(transactionInterceptor());
   		if (this.enableTx != null) {
   			advisor.setOrder(this.enableTx.<Integer>getNumber("order"));
   		}
   		return advisor;
   	}
       
       @Bean
   	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   	public TransactionAttributeSource transactionAttributeSource() {
   		return new AnnotationTransactionAttributeSource();
   	}
       
       @Bean
   	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   	public TransactionInterceptor transactionInterceptor() {
   		TransactionInterceptor interceptor = new TransactionInterceptor();
   		interceptor.setTransactionAttributeSource(transactionAttributeSource());
   		if (this.txManager != null) {
   			interceptor.setTransactionManager(this.txManager);
   		}
   		return interceptor;
   	}
   }
   ```


   ####作用

   1. 添加组件 BeanFactoryTransactionAttributeSourceAdvisor	 事务增强器, 解析事务注解

   2. TransactionInterceptor(MethodInterceptor) 事务拦截器; 保存了事务属性信息, 事务管理器

      目标方法执行后

      1. 执行拦截器链

      2. 执行事务拦截器 invoke()

         1. 获取事务相关属性; @Transactional() 中属性
         2. 获取事务管理器 PlatformTransactionManager
         3. 
            1. 正常: 提交事务
            2. 异常: 回滚事务

         4. 执行标注了 @Transactional 注解的方法

      ```java
      public class TransactionInterceptor extends TransactionAspectSupport implements MethodInterceptor, Serializable {
      		public TransactionInterceptor(PlatformTransactionManager ptm, Properties 
                                            attributes) {
      		setTransactionManager(ptm);
      		setTransactionAttributes(attributes);
      	}
          public Object invoke(MethodInvocation invocation) throws Throwable {
      		Class<?> targetClass = (invocation.getThis() != null ? 
                                 AopUtils.getTargetClass(invocation.getThis()) : null);
              // 在目标方法执行后调用
      		return invokeWithinTransaction(invocation.getMethod(), targetClass, 
                                             invocation::proceed);
      	}
          
          protected Object invokeWithinTransaction(Method method, @Nullable Class<?> 
          	targetClass,final InvocationCallback invocation) throws Throwable {

      		// 获取去事务属性
      		TransactionAttributeSource tas = getTransactionAttributeSource();
      		final TransactionAttribute txAttr = (tas != null ? 
              	tas.getTransactionAttribute(method, targetClass) : null);
              // 事务管理器
      		final PlatformTransactionManager tm = determineTransactionManager(txAttr);
              // 事务管理器名称, 如果指定了名称, 就根据指定名称获取;
              // 如果没有指定事务管理名称, 就从容器中获取一个事务管理器; 
              // 所以在配置类中我们进行了事务管理的注入
      		final String joinpointIdentification = methodIdentification(method, 
                                                     targetClass, txAttr);
      		if (txAttr == null || !(tm instanceof
                	CallbackPreferringPlatformTransactionManager)) {
                  // 创建事务
                  TransactionInfo txInfo = createTransactionIfNecessary(tm, txAttr, 
                                           joinpointIdentification);
      			Object retVal;
      			try {
      				// This is an around advice: Invoke the next interceptor in the 
                      // chain. 调用拦截器链
      				retVal = invocation.proceedWithInvocation();
      			}
      			catch (Throwable ex) {
      				// target invocation exception
                      // 如果一场, 事务回滚
      				completeTransactionAfterThrowing(txInfo, ex);
      				throw ex;
      			}
      			finally {
                      // 最后清除缓存信息
      				cleanupTransactionInfo(txInfo);
      			}
                  // 一切正常, 提交事务
      			commitTransactionAfterReturning(txInfo);
      			return retVal;
      		}
      		else {
      			final ThrowableHolder throwableHolder = new ThrowableHolder();
      			try {
      				Object result = ((CallbackPreferringPlatformTransactionManager) 
                      	tm).execute(txAttr, status -> {TransactionInfo txInfo = 
                          	prepareTransactionInfo(tm, txAttr, 
      						joinpointIdentification, status);
      					try {
      						return invocation.proceedWithInvocation();
      					}
      					catch (Throwable ex) {
      						if (txAttr.rollbackOn(ex)) {
      							// A RuntimeException: will lead to a rollback.
      							if (ex instanceof RuntimeException) {
      								throw (RuntimeException) ex;
      							}
      							else {
      								throw new ThrowableHolderException(ex);
      							}
      						}
      						else {
      							// A normal return value: will lead to a commit.
      							throwableHolder.throwable = ex;
      							return null;
      						}
      					}
      					finally {
      						cleanupTransactionInfo(txInfo);
      					}
      				});

      				// Check result state: It might indicate a Throwable to rethrow.
      				if (throwableHolder.throwable != null) {
      					throw throwableHolder.throwable;
      				}
      				return result;
      			}
      			catch (ThrowableHolderException ex) {
      				throw ex.getCause();
      			}
      			catch (TransactionSystemException ex2) {
      				if (throwableHolder.throwable != null) {
      					logger.error("Application exception overridden by commit exception", throwableHolder.throwable);
      					ex2.initApplicationException(throwableHolder.throwable);
      				}
      				throw ex2;
      			}
      			catch (Throwable ex2) {
      				if (throwableHolder.throwable != null) {
      					logger.error("Application exception overridden by commit exception", throwableHolder.throwable);
      				}
      				throw ex2;
      			}
      		}
      	}
      }
      ```


## Spring 容器创建

