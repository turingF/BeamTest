package PipelineOption;

import PipelineOption.MyTestOption;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

/**
 * @Author: turingF
 * @Date: 2021/10/16 5:03 上午
 * @Description:
 **/
public class POptionTest {
    public static void main(String[] args) {
        MyTestOption myTestOption = PipelineOptionsFactory
// 读入args数组，不一定来自main函数入口
                .fromArgs(args)
// withValidation指需要对args里的所有参数做校验，如果有不存在Option里的参数键值就会抛异常
                .withValidation()
// withoutStrictParsing指不需要做校验，可以有不存在Option里的参数输入
//.withoutStrictParsing()
// 通过as进行最后的生成操作
                .as(MyTestOption.class);

        String myName = myTestOption.getMyName();
        System.out.println("myName=" + myName);
        myTestOption.setMyName("helloWorld");
        System.out.println("myName=" + myTestOption.getMyName());
    }
}
