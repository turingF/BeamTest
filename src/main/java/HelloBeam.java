import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

/**
 * @Author: turingF
 * @Date: 2021/10/15 4:56 下午
 * @Description:  测试beam api，主要涉及创建管道，创建拓扑结构，创建transform
 **/
public class HelloBeam {
    public static void main(String[] args) {
        PipelineOptions options = PipelineOptionsFactory.create();
        // Pipeline 可以认为是一个DAG图
        Pipeline p = Pipeline.create(options);
        // PCollection 认为是数据集
        // apply的是对数据的操作，称为PTransform
        PCollection <String> pcStart = p.apply(Create.of(
                "hello",
                "beam",
                "good luck"
        ));

        PCollection <String> pcMid = pcStart.apply(ParDo.of(new UpperFn()));
        pcMid.apply(ParDo.of(new PrintFn()));

        // 定义DAG结构后，需要调用run来运行
        p.run().waitUntilFinish();
    }

    static class UpperFn extends DoFn<String, String> {
        @ProcessElement
        public void processElement(ProcessContext context){
            String input = context.element();
            String output = input.toUpperCase();
            context.output(output);
        }
    }

    static class PrintFn extends DoFn<String, String> {
        @ProcessElement
        public void processElement(ProcessContext context){
            String input = context.element();
            System.out.println(input);
        }
    }

}
