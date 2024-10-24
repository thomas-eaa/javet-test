package test;

import java.io.File;

import com.caoccao.javet.enums.JSRuntimeType;
import com.caoccao.javet.enums.V8AwaitMode;
import com.caoccao.javet.interop.NodeRuntime;
import com.caoccao.javet.interop.callback.JavetBuiltInModuleResolver;
import com.caoccao.javet.interop.engine.IJavetEngine;
import com.caoccao.javet.interop.engine.JavetEnginePool;
import com.caoccao.javet.interop.executors.IV8Executor;
import com.caoccao.javet.utils.JavetOSUtils;
import com.caoccao.javet.values.reference.V8ValuePromise;

public class JavetTest {

	public static void main(String[] args) throws Exception {
		try (JavetEnginePool<NodeRuntime> javetEnginePool = new JavetEnginePool<NodeRuntime>()) {
		    javetEnginePool.getConfig().setJSRuntimeType(JSRuntimeType.Node);
		    try (IJavetEngine<NodeRuntime> iJavetEngine = javetEnginePool.getEngine()) {
		    	long tstart = System.currentTimeMillis();
		        NodeRuntime nodeRuntime = iJavetEngine.getV8Runtime();
		        nodeRuntime.setV8ModuleResolver(new JavetBuiltInModuleResolver());
//		        File workingDirectory = new File(JavetOSUtils.WORKING_DIRECTORY, "scripts");
		        // Set the require root directory so that Node.js is able to locate node_modules.
//		        nodeRuntime.getNodeModule(NodeModuleModule.class).setRequireRootDirectory(workingDirectory);
		        IV8Executor executor = nodeRuntime.getExecutor(new File(JavetOSUtils.WORKING_DIRECTORY, "scripts/dist/serverrender.js"));
		        executor.setModule(true);
		        V8ValuePromise promise = executor.execute();
		        nodeRuntime.await();
		        System.out.println("\n\ntook " + (System.currentTimeMillis() - tstart) + "ms");
		    }
		}
	}

}
