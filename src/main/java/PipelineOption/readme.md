PipelineOption用处：
1. 传递变量，可以理解为存入option的数据在整个pipeline都存在，不需要写VO来传递数据
2. 设置运行引擎，默认采用的是DirectRunner(本地计算引擎)，可以底层切换成spark,hadoop等
