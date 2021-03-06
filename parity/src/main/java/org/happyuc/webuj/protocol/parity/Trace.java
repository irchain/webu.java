package org.happyuc.webuj.protocol.parity;

import java.math.BigInteger;
import java.util.List;

import org.happyuc.webuj.protocol.core.DefaultBlockParameter;
import org.happyuc.webuj.protocol.core.Request;
import org.happyuc.webuj.protocol.core.methods.request.ReqTransaction;
import org.happyuc.webuj.protocol.parity.methods.request.TraceFilter;
import org.happyuc.webuj.protocol.parity.methods.response.ParityFullTraceResponse;
import org.happyuc.webuj.protocol.parity.methods.response.ParityTraceGet;
import org.happyuc.webuj.protocol.parity.methods.response.ParityTracesResponse;

/**
 * * JSON-RPC Parity traces API request object building factory.
 */
public interface Trace {
    Request<?, ParityFullTraceResponse> traceCall(ReqTransaction reqTransaction, List<String> traceTypes, DefaultBlockParameter blockParameter);

    Request<?, ParityFullTraceResponse> traceRawTransaction(String data, List<String> traceTypes);

    Request<?, ParityFullTraceResponse> traceReplayTransaction(String hash, List<String> traceTypes);

    Request<?, ParityTracesResponse> traceBlock(DefaultBlockParameter blockParameter);

    Request<?, ParityTracesResponse> traceFilter(TraceFilter traceFilter);

    Request<?, ParityTraceGet> traceGet(String hash, List<BigInteger> indices);

    Request<?, ParityTracesResponse> traceTransaction(String hash);
}
