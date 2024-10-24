import { Transform } from 'node:stream'
import { render } from './entry-server.tsx'


// Constants
const ABORT_DELAY = 500
const LOG_MUTED = true

const url = "http://localhost/foo";

const res = process.stdout
if (LOG_MUTED) {
  console.log = console.info = console.warn = console.error = function() {
    //muted
  }
}

const { pipe, abort } = render(url, undefined, {    
    onShellError() {      
      res.write('<h1>Something went wrong</h1>')
    },
    onShellReady() {
      const transformStream = new Transform({
        transform(chunk, encoding, callback) {
          res.write(chunk, encoding)
          callback()
        }
      })
      transformStream.on('finish', () => {
        clearTimeout(timeout);
		    res.write('\n');
        //process.exit(0);
      })

      pipe(transformStream)
    },
    onError(error) {
      console.error(error)
    }
});

const timeout = setTimeout(() => {
  console.warn('abort by timeout', ABORT_DELAY)
  abort()
}, ABORT_DELAY)