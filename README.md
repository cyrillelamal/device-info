# device-info

Simple CLI utility to detect and observe HIDs.

## How to use

Simply build an executable, for example using Maven `mvn package`.

Run the created executable without arguments to run the program in the detector mode.

```shell
# When the utility runs in this mode, you will see attached and detached HIDs in real time.
java -jar ./target/device-info
...
Detecting HIDs
Use Ctrl-C to exit
...
Attached: HidServicesEvent{hidDevice=HidDevice [path=\\?\hid#vid_045e&pid_028e&ig_00#8&1cc3b78c&0&0000#{4d1e55b2-f16f-11cf-88cb-001111000030}, vendorId=0x45e, productId=0x28e, serialNumber=null, releaseNumber=0x0, manufacturer=null,
 product=Controller (XBOX 360 For Windows), usagePage=0x1, usage=0x5, interfaceNumber=-1]}
...
Detached: HidServicesEvent{hidDevice=HidDevice [path=\\?\hid#vid_045e&pid_028e&ig_00#8&1cc3b78c&0&0000#{4d1e55b2-f16f-11cf-88cb-001111000030}, vendorId=0x45e, productId=0x28e, serialNumber=null, releaseNumber=0x0, manufacturer=null,
 product=Controller (XBOX 360 For Windows), usagePage=0x1, usage=0x5, interfaceNumber=-1]}
```

Provide a vendor id and a product id as valid integers (hexes or decimals) to run the program in observer mode.

```shell
# In this mode you will also see all attached and detached devices in real time. You will also see the output of the specified device.
java -jar ./target/device-info -v="0x45e" -p="0x28e"
...
Detecting HIDs
Use Ctrl-C to exit
Cannot find device [vendorId=45e, productId=28e, serialNumber=null]
Attached: HidServicesEvent{hidDevice=HidDevice [path=\\?\hid#vid_045e&pid_028e&ig_00#8&1cc3b78c&0&0000#{4d1e55b2-f16f-11cf-88cb-001111000030}, vendorId=0x45e, productId=0x28e, serialNumber=null, releaseNumber=0x0, manufacturer=null,
 product=Controller (XBOX 360 For Windows), usagePage=0x1, usage=0x5, interfaceNumber=-1]}
Observing: HidDevice [path=\\?\hid#vid_045e&pid_028e&ig_00#8&1cc3b78c&0&0000#{4d1e55b2-f16f-11cf-88cb-001111000030}, vendorId=0x45e, productId=0x28e, serialNumber=null, releaseNumber=0x0, manufacturer=null, product=Controller (XBOX
360 For Windows), usagePage=0x1, usage=0x5, interfaceNumber=-1]
[0, -128, -1, 127, 0, -128, -1, -128, 0, -128, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
Detached: HidServicesEvent{hidDevice=HidDevice [path=\\?\hid#vid_045e&pid_028e&ig_00#8&1cc3b78c&0&0000#{4d1e55b2-f16f-11cf-88cb-001111000030}, vendorId=0x45e, productId=0x28e, serialNumber=null, releaseNumber=0x0, manufacturer=null,
 product=Controller (XBOX 360 For Windows), usagePage=0x1, usage=0x5, interfaceNumber=-1]}
Cannot find device [vendorId=45e, productId=28e, serialNumber=null]
Cannot find device [vendorId=45e, productId=28e, serialNumber=null]
Cannot find device [vendorId=45e, productId=28e, serialNumber=null]
...
```
