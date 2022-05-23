pragma solidity ^0.8.0;

contract Task {

    struct person {
        string nickname;
        string start_date;
        string end_date;
        uint kilometers;
    }

    person user;

    address smartTrackerTeam;
    address user_address;

    uint64 days_count;
    uint64 count = 0;
    uint target;
    uint passed;
    uint public balanceRecived;
    address payable myAddress;
    address payable personal_address;

    //

    modifier onlySmartTrackerTeam(){
        require(msg.sender==smartTrackerTeam);
        _;
    }


    constructor() public {
        //push here
        target = user.kilometers;
        personal_address = payable(msg.sender);
        myAddress = payable(0x3aeAEb4c72471dEF52ccbb4EB277Ea8F5941C95e);

    }


    function showPerson() public view returns (person memory) {
        return user;
    }

    function editName(string memory newName) public onlySmartTrackerTeam {
        user.nickname = newName;
    }

    //

    function receiveMoney() public payable {
        balanceRecived += msg.value;
    }

    function setTarget(uint _target) public {
        target = _target;
        user.kilometers = _target;
    }

    function addPassed(uint _passed) public {
        passed = _passed;
    }

    function addDays(uint32 _days_count) public {
        days_count = _days_count;
    }

    function getBalance() public view returns(uint) {
        return address (this).balance;
    }

    function destroySmartContract(address payable _to) private {
        selfdestruct(_to);
    }

    function checkRes() public {
        if (target <= passed) {
            count += 1;
            //target = 0;
            passed = 0;
            if (count == days_count) {
                destroySmartContract(personal_address);
            }
        }

        else {
            destroySmartContract(myAddress);
        }
    }

}