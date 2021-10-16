package PipelineOption;

import org.apache.beam.sdk.options.PipelineOptions;

/**
 * @Author: turingF
 * @Date: 2021/10/16 5:04 上午
 * @Description:
 **/
public interface MyTestOption extends PipelineOptions {
    String getMyName();
    void setMyName(String name);
}
