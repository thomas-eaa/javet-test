import React, { Suspense, lazy } from 'react'
import { renderToPipeableStream, RenderToPipeableStreamOptions } from 'react-dom/server'

const Detail = lazy(() => import("./Detail"));

function App() {
	return (<><h1>My App</h1>
			<Suspense fallback={<div>Loading...</div>}>
				<Detail />
			</Suspense>		
		</>);
}

export function render(_url: string, _ssrManifest: string, options: RenderToPipeableStreamOptions) {
	return renderToPipeableStream(
	    <React.StrictMode>
	      <App />
	    </React.StrictMode>,
	    options
	  )	
}
