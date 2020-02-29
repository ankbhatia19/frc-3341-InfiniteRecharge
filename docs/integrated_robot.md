# Integrated Robot Software Documentation

## Requirements
Insert a high level description of what this system is attempting to do, but be detailed about it. Think of it like your module’s software “playbook” (like in football). For example, an autonomous description could be:

> During the TeleOP and autonomous period, the robot will be using a four motor drive that can be inverted with a button for the driver's convenience. The button will also switch the view of the camera from the front to back or vice verse. Once the power cells are loaded, we plan to use the button to invert the motors instead of turning the entire robot around. Additionally, the shuffleboard will be displaying values to aid the driver.

## Procedure

Describe with more detail exactly how the high level requirement will be executed. Include implementation details, like which algorithms you will be using, what sensors you will be accessing. For example, a description of an autonomous winch operation could be:

> We will be controlling two pairs of master and follower talons with two joysticks (ports 0 and 1). We need to research the method needed for the following talons. For shuffleboard, we looked at the documentation to learn its capabilities. Lastly, we are moving the tank drive out of periodic and into its own command.

## Timeline/Details

Again this a sample, but this is what it would like for the same example as the last:

- [x] **1/7/2020** - Create the software documentation markdown page.
- [x] **1/9/2020** - Get shuffleboard working and displaying values.
- [x] **2/15/2020** - Make tank drive into its own command.
- [x] **2/15/2020** - Make a button that inverts the drive train motors
- [ ] **2/18/2020** - Set up the front and back camera
- [ ] **2/18/2020** - Make that same button invert the cameras

