package com.example.demo.service

import com.example.demo.exceptions.CancelOfferException
import com.example.demo.exceptions.UserNotFoundException
import com.example.demo.exceptions.UserOfferCreationException
import com.example.demo.model.OfferStatus
import com.example.demo.model.OfferTypeHelper
import com.example.demo.model.UserOffer
import com.example.demo.repository.UserOfferRepository
import com.example.demo.repository.UserRepository
import com.example.demo.service.Proxys.ProxyUsdPrice
import com.example.demo.request.UserOfferRequest
import org.springframework.aot.generate.Generated
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.math.abs

@Generated
@Service
@Transactional
class UserOfferService {

    @Autowired
    lateinit var userOfferRepository : UserOfferRepository
    @Autowired
    lateinit var userRepository : UserRepository
    var proxyUsdPrice = ProxyUsdPrice()

    fun publishOffer(userOfferRequest: UserOfferRequest) : UserOffer {
        try {
            val userOffer = this.createUserOffer(userOfferRequest)
            userOfferRepository.save(userOffer)
            return userOffer
       }catch (e: Exception) {
                throw UserOfferCreationException("Failed to create user: ${e.message}")
        }
    }

    fun allOffers(): List<UserOffer>{
        return userOfferRepository.findAll()
    }

    // extra : borrar oferta


    private fun createUserOffer(userOfferRequest: UserOfferRequest): UserOffer {

        val user = userRepository.findById(userOfferRequest.userId.toLong())
            .orElseThrow { throw  UserNotFoundException("user with id ${userOfferRequest.userId} dont exist")}

        val cryptoPriceResult = cryptoCheck(userOfferRequest.cryptoPrice, userOfferRequest.cryptoSymbol)
        val totalArgsMounts = calculateThePriceInPesos(userOfferRequest.cryptoPrice,userOfferRequest.cryptoMounts)

        val newUserOffer =                       // pasarlo a otro lado
            UserOffer.UserOfferBuilder()
                .cryptoSymbol(userOfferRequest.cryptoSymbol)
                .cryptoMounts(userOfferRequest.cryptoMounts)
                .cryptoPrice(cryptoPriceResult)
                .argsMounts(totalArgsMounts)
                .user(user)
                .offerDate(Date())
                .offerType(OfferTypeHelper.transform(userOfferRequest.offerType))
                .offerStatus(OfferStatus.AVAILABLE)
                .build()

        //userOfferRepository.save(newUserOffer) --> Test

        return newUserOffer
    }

    private fun calculateThePriceInPesos(cryptoPrice: Double, cryptoMounts: Double): Double{
        val argCryptoPrice = proxyUsdPrice.convertUSDPrice(cryptoPrice)
        return argCryptoPrice * cryptoMounts
    }

    fun allOffersFrom(userId: Long): List<UserOffer> {
        return userOfferRepository.findAllByUserIdAndOfferStatus(userId,OfferStatus.AVAILABLE)
    }

    fun cancelOffer(userId: String, offerId: String) {
        try {
            val offer = userOfferRepository.findById(offerId.toLong()).get()
            offer.validateCancelTheOffer(userId)
            userOfferRepository.delete(offer)

        } catch (e : Exception){
            throw CancelOfferException("Cant cancel the offer $offerId because ${e.message}")
        }
    }

    private fun cryptoCheck(cryptoPrice: Double, cryptoSymbol:String): Double{
        if(isValidCryptoPrice(cryptoPrice, cryptoSymbol)){
            return cryptoPrice
        }
        else{
            throw UserOfferCreationException("The crypto Price $cryptoPrice not valid")
        }
    }
    private fun isValidCryptoPrice(cryptoPrice: Double, cryptoSymbol:String): Boolean {
        val lastCryptoValue = CryptoService().getCrypto(cryptoSymbol).price.toDouble()
        return percentageDifference(cryptoPrice,lastCryptoValue)
    }
    private fun percentageDifference(price1 : Double, price2 : Double) : Boolean {
        val difference  = abs(price1 - price2)
        val allowedMargin = price1 * 0.05
        return difference <= allowedMargin
    }
}

