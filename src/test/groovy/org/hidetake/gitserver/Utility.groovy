package org.hidetake.gitserver

class Utility {

    static int pickUpFreePort() {
        def socket = new ServerSocket(0)
        def port = socket.localPort
        socket.close()
        port
    }

}
