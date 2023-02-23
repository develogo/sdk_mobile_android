package br.com.dito.samplekotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import br.com.dito.ditosdk.*
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickIdentify(view: View) {
        val data = CustomData()
        data.add("userId",  "uol")
        data.add("push", false)

        val identify = Identify("85496430259")
        identify.data = data

        Dito.identify(identify, { registerToken() })
    }

    fun registerToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            token = it?.token
            Dito.registerDevice(token!!)
            Log.d("identify", "success on registering token")
        }
    }

    fun onClickEvent(view: View) {
        Dito.track(Event("comprou", 2.5))
    }

    fun onClickRegisterToken(view: View) {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            token = it?.token
            Dito.registerDevice(token!!)
        }
    }

    fun onClickDeleteToken(view: View) {
        token?.let {
            Dito.unregisterDevice(it)
        }
    }
}
