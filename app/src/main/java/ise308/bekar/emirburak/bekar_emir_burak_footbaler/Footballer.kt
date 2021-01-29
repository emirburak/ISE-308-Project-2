package ise308.bekar.emirburak.bekar_emir_burak_footbaler

class Footballer{
    var id : Long ?= null
    var name : String ?= null
    var surname : String ?= null
    var age :Int ?= null
    var position : String ?= null
    var isAvailableForPlaying : Boolean ?= null
    var teamName: String ?= null

    constructor(id: Long?, name: String?, surname: String?, age: Int?, position: String?,
                isAvailableForPlaying: Int?, teamName: String?){
        this.id = id
        this.name = name
        this.surname = surname
        this.age = age
        this.position = position
        this.isAvailableForPlaying = isAvailableForPlaying==1 //constructor get int isAVailable variable because there is no boolean data type in sqlite
        this.teamName = teamName
    }


}