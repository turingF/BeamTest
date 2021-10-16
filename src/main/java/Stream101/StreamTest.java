package Stream101;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.joda.time.Duration;
import org.joda.time.Instant;

import java.util.List;

/**
 * Author: TuringF
 * Create time: 2021/10/16
 * Description:
 **/
public class StreamTest {
    public static void main(String[] args) {

        final Long NOW_TIME = System.currentTimeMillis();
        List<KV<Integer, String>> kvList = new ArrayList<>();
        kvList.add(KV.of(999,"0.999s"));
        kvList.add(KV.of(1000,"1.0s"));
        kvList.add(KV.of(1001,"1.001s"));
        kvList.add(KV.of(1999,"1.999s"));
        kvList.add(KV.of(2000,"2.0s"));
        kvList.add(KV.of(2500,"2.5s"));
        kvList.add(KV.of(3000,"3.0s"));
        kvList.add(KV.of(4000,"4.0s"));
        kvList.add(KV.of(5000,"5.0s"));

        Pipeline pipeline = Pipeline.create();
        PCollection<String> pTimeStr = pipeline.apply(Create.of(kvList))
                // 给每个输入元素设置Instant为(某个固定时间 + key)
                .apply(WithTimestamps.of(new SimpleFunction<KV<Integer, String>, Instant>() {
                    @Override
                    public Instant apply(KV<Integer, String> input) {
                        return new Instant(NOW_TIME + input.getKey());
                    }
                }))
                // 把Key-value转成value
                .apply(Values.<String>create());

        // 创建窗口,如固定窗口，滑动窗口，会话窗口
        PCollection<String> pTimeStrByWindow = pTimeStr.apply(Window.into(FixedWindows.of(Duration.standardSeconds(1))));
        // 同1个窗口内的合成1个列表,最多聚合100个
        pTimeStrByWindow.apply(Combine.globally(Sample.<String>anyCombineFn(100)).withoutDefaults())
                // 输出
                .apply(ParDo.of(new PrintStrFn()));

        pipeline.run().waitUntilFinish();
    }
}
