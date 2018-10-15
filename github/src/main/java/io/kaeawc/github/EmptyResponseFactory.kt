package io.kaeawc.github

import io.kaeawc.domain.Empty
import java.lang.reflect.Type

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * Created by thientvse on 18/05/2018.
 */

class EmptyResponseFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter<ResponseBody, Any> { body -> if (body.contentLength() == 0L) Empty() else delegate.convert(body) }
    }
}
