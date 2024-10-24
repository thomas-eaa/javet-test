package test;

import com.caoccao.javet.enums.JSRuntimeType;
import com.caoccao.javet.interop.NodeRuntime;
import com.caoccao.javet.interop.engine.IJavetEngine;
import com.caoccao.javet.interop.engine.JavetEnginePool;
import com.caoccao.javet.interop.executors.IV8Executor;

public class JavetTest {

	public static void main(String[] args) throws Exception {
		try (JavetEnginePool<NodeRuntime> javetEnginePool = new JavetEnginePool<NodeRuntime>()) {
		    javetEnginePool.getConfig().setJSRuntimeType(JSRuntimeType.Node);
		    try (IJavetEngine<NodeRuntime> iJavetEngine = javetEnginePool.getEngine()) {
		        NodeRuntime nodeRuntime = iJavetEngine.getV8Runtime();
		        IV8Executor executor = nodeRuntime.getExecutor(
		                "import { Transform } from 'node:stream'; console.log('could import stream transform')");
		        executor.setModule(true);
		        System.out.println(executor.executeString());
		    }
		}
	}

}
