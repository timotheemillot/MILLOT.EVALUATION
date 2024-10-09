package org.mathieu.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.mathieu.data.remote.responses.LocationPreviewResponse
import org.mathieu.data.remote.responses.LocationResponse

/**
 *
 */
internal class LocationApi(private val client: HttpClient) {

    /**
     * Fetches the details of a location with the given ID from the service.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return The [LocationResponse] representing the details of the location.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getLocation(id: Int): LocationResponse? = client
        .get("location/$id")
        .accept(HttpStatusCode.OK)
        .body()

    /**
     * Fetches the details of a location preview with the given ID from the service.
     * It is a lighter version of the location details.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return The [LocationPreviewResponse] representing the details of the location.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getLocationPreview(id : Int): LocationPreviewResponse? = client
        .get("location/$id")
        .accept(HttpStatusCode.OK)
        .body()
}