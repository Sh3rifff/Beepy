package az.sharif.beepy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import az.sharif.beepy.databinding.ServisRecyclerRowBinding
import az.sharif.beepy.model.ServisModel

class ServiceAdapter(private val servicesList : ArrayList<ServisModel>) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ServisRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ServiceViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.binding.serviceName.text = servicesList[position].name
        holder.binding.serviceCategory.text = servicesList[position].category
        holder.binding.serviceImage.setImageResource(servicesList[position].image)
//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context,FutureActivity::class.java)
//            intent.putExtra("landmark", servicesList[position])
//            holder.itemView.context.startActivity(intent)
//        }
    }
    override fun getItemCount(): Int {
        return servicesList.size
    }

    class ServiceViewHolder (val binding: ServisRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)
}