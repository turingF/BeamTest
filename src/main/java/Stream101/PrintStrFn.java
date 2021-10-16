package Stream101;

/**
 * Author: TuringF
 * Create time: 2021/10/16
 * Description:
 **/
public class PrintStrFn extends org.apache.beam.sdk.transforms.DoFn<String, String> {
    @ProcessElement
    public void processElement(ProcessContext context){
        String input = context.element();
        System.out.println(input);
    }
}
